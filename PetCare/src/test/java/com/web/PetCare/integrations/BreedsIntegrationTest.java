package com.web.PetCare.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.PetCare.PetCareApplication;
import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.dtos.OwnerDTO;
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
public class BreedsIntegrationTest {

    private String host = "http://localhost";

    private static final String REQUEST_PATH_GET_BREEDS = "%s/breeds/breed";
    private static final String REQUEST_PATH_CREATE_BREED = "%s/breeds/breed";

    private static final String REQUEST_PATH_DELETE_BREEDS = "%s/breeds/breed/%s";

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
    public void shouldGetAllBreeds() throws Exception {
        BreedDTO breedDTO = defaultBreedDto();
        String breedDtoStr = objectMapper.writeValueAsString(breedDTO);
        RequestBuilder createBreed = post(String.format(REQUEST_PATH_CREATE_BREED, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(breedDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createBreed).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final BreedDTO actualBreedDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BreedDTO.class);
        assertNotNull(actualBreedDto);
        assertEquals(breedDTO.getName(), actualBreedDto.getName());
        assertEquals(breedDTO.getDescription(), actualBreedDto.getDescription());

        RequestBuilder getAllBreeds = get(String.format(REQUEST_PATH_GET_BREEDS, baseUri));
        final MvcResult mvcResult2 = mockMvc.perform(getAllBreeds).andExpect(status().isOk()).andReturn();
        final List<BreedDTO> actualBreedDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), BreedDTO[].class));

        assertFalse(actualBreedDtoList.isEmpty());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_BREEDS, baseUri, actualBreedDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldCreateBreed() throws Exception {
        BreedDTO breedDTO = defaultBreedDto();
        String breedDtoStr = objectMapper.writeValueAsString(breedDTO);
        RequestBuilder createBreed = post(String.format(REQUEST_PATH_CREATE_BREED, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(breedDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createBreed).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final BreedDTO actualBreedDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BreedDTO.class);
        assertNotNull(actualBreedDto);
        assertEquals(breedDTO.getName(), actualBreedDto.getName());
        assertEquals(breedDTO.getDescription(), actualBreedDto.getDescription());
        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_BREEDS, baseUri, actualBreedDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldDeleteBreed() throws Exception {
        BreedDTO breedDTO = defaultBreedDto();
        String breedDtoStr = objectMapper.writeValueAsString(breedDTO);
        RequestBuilder createBreed = post(String.format(REQUEST_PATH_CREATE_BREED, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(breedDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createBreed).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final BreedDTO actualBreedDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BreedDTO.class);
        assertNotNull(actualBreedDto);
        assertEquals(breedDTO.getName(), actualBreedDto.getName());
        assertEquals(breedDTO.getDescription(), actualBreedDto.getDescription());
        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_BREEDS, baseUri, actualBreedDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
    }

    private BreedDTO defaultBreedDto() {
        return new BreedDTO().name("testNameBreed");
    }

}
