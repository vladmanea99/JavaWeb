package com.web.PetCare.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.PetCare.PetCareApplication;
import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.dtos.TreatmentDTO;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PetCareApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TreatmentsIntegrationTest {

    private String host = "http://localhost";

    private static final String REQUEST_PATH_GET_TREATMENTS = "%s/treatments/treatment";
    private static final String REQUEST_PATH_CREATE_TREATMENT = "%s/treatments/treatment";

    private static final String REQUEST_PATH_GET_TREATMENTS_PER_PET = "%s/treatments/allTreatmentsPet/%s";

    private static final String REQUEST_PATH_DELETE_TREATMENT = "%s/treatments/treatment/%s";

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
    public void shouldGetAllTreatments() throws Exception {
        TreatmentDTO treatmentDTO = defaultTreatmentDto();
        String treatmentDtoStr = objectMapper.writeValueAsString(treatmentDTO);
        RequestBuilder createTreatment = post(String.format(REQUEST_PATH_CREATE_TREATMENT, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(treatmentDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createTreatment).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final TreatmentDTO actualTreatmentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TreatmentDTO.class);
        assertNotNull(actualTreatmentDto);
        assertEquals(treatmentDTO.getName(), actualTreatmentDto.getName());
        assertEquals(treatmentDTO.getDescription(), actualTreatmentDto.getDescription());

        RequestBuilder getAllTreatments = get(String.format(REQUEST_PATH_GET_TREATMENTS, baseUri));
        final MvcResult mvcResult2 = mockMvc.perform(getAllTreatments).andExpect(status().isOk()).andReturn();
        final List<TreatmentDTO> actualTreatmentDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), TreatmentDTO[].class));

        assertFalse(actualTreatmentDtoList.isEmpty());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_TREATMENT, baseUri, actualTreatmentDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldCreateOwner() throws Exception {
        TreatmentDTO treatmentDTO = defaultTreatmentDto();
        String treatmentDtoStr = objectMapper.writeValueAsString(treatmentDTO);
        RequestBuilder createTreatment = post(String.format(REQUEST_PATH_CREATE_TREATMENT, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(treatmentDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createTreatment).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final TreatmentDTO actualTreatmentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TreatmentDTO.class);
        assertNotNull(actualTreatmentDto);
        assertEquals(treatmentDTO.getName(), actualTreatmentDto.getName());
        assertEquals(treatmentDTO.getDescription(), actualTreatmentDto.getDescription());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_TREATMENT, baseUri, actualTreatmentDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldDeleteOwner() throws Exception {
        TreatmentDTO treatmentDTO = defaultTreatmentDto();
        String treatmentDtoStr = objectMapper.writeValueAsString(treatmentDTO);
        RequestBuilder createTreatment = post(String.format(REQUEST_PATH_CREATE_TREATMENT, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(treatmentDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createTreatment).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final TreatmentDTO actualTreatmentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TreatmentDTO.class);
        assertNotNull(actualTreatmentDto);
        assertEquals(treatmentDTO.getName(), actualTreatmentDto.getName());
        assertEquals(treatmentDTO.getDescription(), actualTreatmentDto.getDescription());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_TREATMENT, baseUri, actualTreatmentDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetTreatmentsPerPetTest() throws Exception {
        RequestBuilder getAllTreatments = get(String.format(REQUEST_PATH_GET_TREATMENTS_PER_PET, baseUri, "1"));
        final MvcResult mvcResult2 = mockMvc.perform(getAllTreatments).andExpect(status().isOk()).andReturn();
        final List<TreatmentDTO> actualTreatmentDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), TreatmentDTO[].class));
    }

    private TreatmentDTO defaultTreatmentDto() {
        return new TreatmentDTO().name("maggots removal");
    }
}
