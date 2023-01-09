package com.web.PetCare.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.PetCare.PetCareApplication;
import com.web.PetCare.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PetCareApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessionIntegrationTest {

    private String host = "http://localhost";

    private static final String REQUEST_PATH_GET_SESSIONS = "%s/sessions/session";
    private static final String REQUEST_PATH_CREATE_SESSION = "%s/sessions/session";
    private static final String REQUEST_PATH_GET_SESSIONS_PAID = "%s/sessions/sessionsPaid";

    private static final String REQUEST_PATH_DELETE_SESSION = "%s/sessions/session/%s";

    @LocalServerPort
    private String port;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private String baseUri;

    @BeforeEach
    public void setUp() {
        baseUri = String.format("%s:%s", host, port);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    @Test
    public void shouldGetAllSessions() throws Exception {
        SessionDTO sessionDTO = defaultSessionDto();
        String sessionDtoStr = objectMapper.writeValueAsString(sessionDTO);
        RequestBuilder createSession = post(String.format(REQUEST_PATH_CREATE_SESSION, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(sessionDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createSession).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final SessionDTO actualSessionDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SessionDTO.class);
        assertNotNull(actualSessionDto);
        assertEquals(sessionDTO.getSessionDate().atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime(), actualSessionDto.getSessionDate());
        assertEquals(sessionDTO.getPet().getName(), actualSessionDto.getPet().getName());
        assertEquals(sessionDTO.getTreatment().getName(), actualSessionDto.getTreatment().getName());

        RequestBuilder getAllSessions = get(String.format(REQUEST_PATH_GET_SESSIONS, baseUri));
        final MvcResult mvcResult2 = mockMvc.perform(getAllSessions).andExpect(status().isOk()).andReturn();
        final List<SessionDTO> actualSessionDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), SessionDTO[].class));

        assertFalse(actualSessionDtoList.isEmpty());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_SESSION, baseUri, actualSessionDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldCreateOwner() throws Exception {
        SessionDTO sessionDTO = defaultSessionDto();
        String sessionDtoStr = objectMapper.writeValueAsString(sessionDTO);
        RequestBuilder createSession = post(String.format(REQUEST_PATH_CREATE_SESSION, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(sessionDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createSession).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final SessionDTO actualSessionDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SessionDTO.class);
        assertNotNull(actualSessionDto);
        assertEquals(sessionDTO.getSessionDate().atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime(), actualSessionDto.getSessionDate());
        assertEquals(sessionDTO.getPet().getName(), actualSessionDto.getPet().getName());
        assertEquals(sessionDTO.getTreatment().getName(), actualSessionDto.getTreatment().getName());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_SESSION, baseUri, actualSessionDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteOwner() throws Exception {
        SessionDTO sessionDTO = defaultSessionDto();
        String sessionDtoStr = objectMapper.writeValueAsString(sessionDTO);
        RequestBuilder createSession = post(String.format(REQUEST_PATH_CREATE_SESSION, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(sessionDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createSession).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final SessionDTO actualSessionDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SessionDTO.class);
        assertNotNull(actualSessionDto);
        assertEquals(sessionDTO.getSessionDate().atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime(), actualSessionDto.getSessionDate());
        assertEquals(sessionDTO.getPet().getName(), actualSessionDto.getPet().getName());
        assertEquals(sessionDTO.getTreatment().getName(), actualSessionDto.getTreatment().getName());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_SESSION, baseUri, actualSessionDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetSessionsPaidTest() throws Exception {
        RequestBuilder getAllSessionsPaid = get(String.format(REQUEST_PATH_GET_SESSIONS_PAID, baseUri));
        final MvcResult mvcResult2 = mockMvc.perform(getAllSessionsPaid).andExpect(status().isOk()).andReturn();
        final List<SessionDTO> actualSessionDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), SessionDTO[].class));

    }

    private SessionDTO defaultSessionDto() {
        return new SessionDTO()
                .sessionDate(OffsetDateTime.now())
                .pet(new PetDTO()
                        .id(1L)
                        .name("Aron")
                        .breed(new BreedDTO().id(34L).name("rottweiler").description("best breed"))
                        .owner(new OwnerDTO().id(5L).firstName("Vlad").lastName("Manea")))
                .treatment(new TreatmentDTO()
                        .id(29L)
                        .name("bath + massage of the fur and body of the pet")
                        .description(null));
    }

}
