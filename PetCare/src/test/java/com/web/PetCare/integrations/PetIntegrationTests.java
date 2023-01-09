package com.web.PetCare.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.PetCare.PetCareApplication;
import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.dtos.PetDTO;
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
public class PetIntegrationTests {

    private String host = "http://localhost";

    private static final String REQUEST_PATH_GET_PETS = "%s/pets/pet";
    private static final String REQUEST_PATH_CREATE_PET = "%s/pets/pet";

    private static final String REQUEST_PATH_GET_PETS_WITH_TREATMENTS = "%s/pets/petWithTreatments";
    private static final String REQUEST_PATH_DELETE_PET = "%s/pets/pet/%s";

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
    public void shouldGetAllOwners() throws Exception {
        PetDTO petDTO = defaultPetDto();
        String petDtoStr = objectMapper.writeValueAsString(petDTO);
        RequestBuilder createPet = post(String.format(REQUEST_PATH_CREATE_PET, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(petDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createPet).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final PetDTO actualPetDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PetDTO.class);
        assertNotNull(actualPetDto);
        assertEquals(petDTO.getName(), actualPetDto.getName());
        assertEquals(petDTO.getOwner().getFirstName(), actualPetDto.getOwner().getFirstName());
        assertEquals(petDTO.getBreed().getName(), actualPetDto.getBreed().getName());

        RequestBuilder getAllPets = get(String.format(REQUEST_PATH_GET_PETS, baseUri));
        final MvcResult mvcResult2 = mockMvc.perform(getAllPets).andExpect(status().isOk()).andReturn();
        final List<PetDTO> actualPetDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), PetDTO[].class));

        assertFalse(actualPetDtoList.isEmpty());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_PET, baseUri, actualPetDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldCreateOwner() throws Exception {
        PetDTO petDTO = defaultPetDto();
        String petDtoStr = objectMapper.writeValueAsString(petDTO);
        RequestBuilder createPet = post(String.format(REQUEST_PATH_CREATE_PET, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(petDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createPet).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final PetDTO actualPetDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PetDTO.class);
        assertNotNull(actualPetDto);
        assertEquals(petDTO.getName(), actualPetDto.getName());
        assertEquals(petDTO.getOwner().getFirstName(), actualPetDto.getOwner().getFirstName());
        assertEquals(petDTO.getBreed().getName(), actualPetDto.getBreed().getName());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_PET, baseUri, actualPetDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldDeleteOwner() throws Exception {
        PetDTO petDTO = defaultPetDto();
        String petDtoStr = objectMapper.writeValueAsString(petDTO);
        RequestBuilder createPet = post(String.format(REQUEST_PATH_CREATE_PET, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(petDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createPet).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final PetDTO actualPetDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PetDTO.class);
        assertNotNull(actualPetDto);
        assertEquals(petDTO.getName(), actualPetDto.getName());
        assertEquals(petDTO.getOwner().getFirstName(), actualPetDto.getOwner().getFirstName());
        assertEquals(petDTO.getBreed().getName(), actualPetDto.getBreed().getName());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_PET, baseUri, actualPetDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetPetsWithTreatmentsTest() throws Exception {
        RequestBuilder getAllPetsWithTreatments = get(String.format(REQUEST_PATH_GET_PETS_WITH_TREATMENTS, baseUri));
        final MvcResult mvcResult2 = mockMvc.perform(getAllPetsWithTreatments).andExpect(status().isOk()).andReturn();
        final List<PetDTO> actualPetDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), PetDTO[].class));

    }

    private PetDTO defaultPetDto() {
        return new PetDTO()
                .name("Aron")
                .breed(new BreedDTO().id(34L).name("rottweiler").description("best breed"))
                .owner(new OwnerDTO().id(5L).firstName("Vlad").lastName("Manea"));
    }


}
