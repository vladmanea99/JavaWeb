package com.web.PetCare.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.*;
import com.web.PetCare.services.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionsApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionService sessionService;

    @Test
    public void getListOfSessionsTest() throws Exception {
        when(sessionService.getSessions()).thenReturn(SESSION_DTO_LIST);

        final MvcResult mvcResult = mockMvc.perform(get("/sessions/session"))
                .andExpect(status().isOk())
                .andReturn();

        final List<SessionDTO> actualSessionDtoList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SessionDTO[].class));
        assertNotNull(actualSessionDtoList);
        assertEquals(2, actualSessionDtoList.size());
        verify(sessionService, times(1)).getSessions();
    }

    @Test
    public void createSessionTest() throws Exception {
        SessionDTO sessionDTO = defaultSessionDto();
        when(sessionService.createSession(any())).thenReturn(sessionDTO);

        String sessionDtoStr = objectMapper.writeValueAsString(sessionDTO);

        RequestBuilder postEvent = post("/sessions/session")
                .accept(MediaType.APPLICATION_JSON)
                .content(sessionDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(postEvent).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        verify(sessionService, times(1)).createSession(any());
        final SessionDTO actualSessionDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SessionDTO.class);
        assertNotNull(actualSessionDto);
        assertEquals(sessionDTO.getSessionDate().atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime(), actualSessionDto.getSessionDate());
        assertEquals(sessionDTO.getTreatment().getId(), actualSessionDto.getTreatment().getId());
        assertEquals(sessionDTO.getTreatment().getName(), actualSessionDto.getTreatment().getName());
        assertEquals(sessionDTO.getTreatment().getDescription(), actualSessionDto.getTreatment().getDescription());
        assertEquals(sessionDTO.getPet().getId(), actualSessionDto.getPet().getId());
        assertEquals(sessionDTO.getPet().getName(), actualSessionDto.getPet().getName());
    }

    private SessionDTO defaultSessionDto() {
        return new SessionDTO()
                .sessionDate(OffsetDateTime.now())
                .pet(new PetDTO()
                        .id(1L)
                        .name("Aron")
                        .breed(new BreedDTO().id(1L).name("rottweiler").description("best breed"))
                        .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea")))
                .treatment(new TreatmentDTO()
                        .id(1L)
                        .name("massage")
                        .description(null));
    }

    private static final List<SessionDTO> SESSION_DTO_LIST = ImmutableList.of(
            new SessionDTO(),
            new SessionDTO()
    );
}
