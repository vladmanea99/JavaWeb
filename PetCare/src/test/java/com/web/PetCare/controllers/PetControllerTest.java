package com.web.PetCare.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.dtos.PetDTO;
import com.web.PetCare.services.PetService;
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
@WebMvcTest(PetsApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PetService petService;

    @Test
    public void getListOfPets() throws Exception {
        when(petService.getAllPets()).thenReturn(PET_DTO_LIST);

        final MvcResult mvcResult = mockMvc.perform(get("/pets/pet"))
                .andExpect(status().isOk())
                .andReturn();

        final List<PetDTO> actualPetDtoList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PetDTO[].class));
        assertNotNull(actualPetDtoList);
        assertEquals(2, actualPetDtoList.size());
        verify(petService, times(1)).getAllPets();
    }

    @Test
    public void createPetTest() throws Exception {
        PetDTO petDTO = defaultPetDto();
        when(petService.createPet(any())).thenReturn(petDTO);

        String petDtoStr = objectMapper.writeValueAsString(petDTO);

        RequestBuilder postEvent = post("/pets/pet")
                .accept(MediaType.APPLICATION_JSON)
                .content(petDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(postEvent).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        verify(petService, times(1)).createPet(any());
        final PetDTO actualPetDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PetDTO.class);
        assertNotNull(actualPetDto);
        assertEquals(petDTO.getName(), actualPetDto.getName());
        assertEquals(petDTO.getBreed().getId(), actualPetDto.getBreed().getId());
        assertEquals(petDTO.getBreed().getName(), actualPetDto.getBreed().getName());
        assertEquals(petDTO.getOwner().getId(), actualPetDto.getOwner().getId());
        assertEquals(petDTO.getOwner().getFirstName(), actualPetDto.getOwner().getFirstName());
    }

    @Test
    public void deletePetTest() throws Exception {
        doNothing().when(petService).deletePet(any());
        RequestBuilder deleteEvent = delete("/pets/pet/100");
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
        verify(petService, times(1)).deletePet(any());
    }

    @Test
    public void getPetsWithTreatmentsTest() throws Exception {
        when(petService.getPetsWithTreatments()).thenReturn(PET_DTO_LIST);

        final MvcResult mvcResult = mockMvc.perform(get("/pets/petWithTreatments"))
                .andExpect(status().isOk())
                .andReturn();

        final List<PetDTO> actualPetDtoList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PetDTO[].class));
        assertNotNull(actualPetDtoList);
        assertEquals(2, actualPetDtoList.size());
        verify(petService, times(1)).getPetsWithTreatments();
    }

    private PetDTO defaultPetDto() {
        return new PetDTO()
                .name("Aron")
                .breed(new BreedDTO().id(1L).name("rottweiler").description("best breed"))
                .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea"));
    }

    private static final List<PetDTO> PET_DTO_LIST = ImmutableList.of(
            new PetDTO(),
            new PetDTO()
    );
}
