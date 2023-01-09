package com.web.PetCare.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.TreatmentDTO;
import com.web.PetCare.services.TreatmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TreatmentsApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TreatmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TreatmentService treatmentService;

    @Test
    public void getListOfTreatments() throws Exception {
        when(treatmentService.getTreatments()).thenReturn(TREATMENT_DTO_LIST);

        final MvcResult mvcResult = mockMvc.perform(get("/treatments/treatment"))
                .andExpect(status().isOk())
                .andReturn();

        final List<TreatmentDTO> actualTreatmentDtoList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TreatmentDTO[].class));
        assertNotNull(actualTreatmentDtoList);
        assertEquals(2, actualTreatmentDtoList.size());
        verify(treatmentService, times(1)).getTreatments();
    }

    @Test
    public void createTreatmentTest() throws Exception {
        TreatmentDTO treatmentDTO = defaultTreatmentDto();
        when(treatmentService.createTreatment(any())).thenReturn(treatmentDTO);

        String treatmentDtoStr = objectMapper.writeValueAsString(treatmentDTO);

        RequestBuilder postEvent = post("/treatments/treatment")
                .accept(MediaType.APPLICATION_JSON)
                .content(treatmentDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(postEvent).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        verify(treatmentService, times(1)).createTreatment(any());

        final TreatmentDTO actualTreatmentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TreatmentDTO.class);
        assertNotNull(actualTreatmentDto);
        assertEquals(treatmentDTO.getName(), actualTreatmentDto.getName());
    }

    @Test
    public void deleteTreatmentTest() throws Exception {
        doNothing().when(treatmentService).deleteTreatment(any());
        RequestBuilder deleteEvent = delete("/treatments/treatment/100");
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
        verify(treatmentService, times(1)).deleteTreatment(any());
    }

    @Test
    public void getListOfTreatmentsPerPetTest() throws Exception {
        when(treatmentService.getTreatmentsPerPet(any())).thenReturn(TREATMENT_DTO_LIST);

        final MvcResult mvcResult = mockMvc.perform(get("/treatments/allTreatmentsPet/1"))
                .andExpect(status().isOk())
                .andReturn();

        final List<TreatmentDTO> actualTreatmentDtoList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TreatmentDTO[].class));
        assertNotNull(actualTreatmentDtoList);
        assertEquals(2, actualTreatmentDtoList.size());
        verify(treatmentService, times(1)).getTreatmentsPerPet(any());
    }

    private static final List<TreatmentDTO> TREATMENT_DTO_LIST = ImmutableList.of(
            new TreatmentDTO(),
            new TreatmentDTO()
    );

    private TreatmentDTO defaultTreatmentDto() {
        return new TreatmentDTO().name("maggots removal");
    }
}
