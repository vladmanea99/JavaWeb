package com.web.PetCare.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.BreedDTO;
import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.services.BreedService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BreedsApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BreedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BreedService breedService;

    @Test
    public void getListOfBreeds() throws Exception {
        when(breedService.getBreeds()).thenReturn(BREED_DTO_LIST);

        final MvcResult mvcResult = mockMvc.perform(get("/breeds/getBreeds"))
                .andExpect(status().isOk())
                .andReturn();

        final List<BreedDTO> actualBreedDtoList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BreedDTO[].class));
        assertNotNull(actualBreedDtoList);
        assertEquals(2, actualBreedDtoList.size());
        verify(breedService, times(1)).getBreeds();
    }

    @Test
    public void createBreed() throws Exception {
        BreedDTO breedDTO = defaultBreedDto();
        when(breedService.createBreed(any())).thenReturn(breedDTO);

        String breedDtoStr = objectMapper.writeValueAsString(breedDTO);

        RequestBuilder postEvent = post("/breeds/createBreed")
                .accept(MediaType.APPLICATION_JSON)
                .content(breedDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(postEvent).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        verify(breedService, times(1)).createBreed(any());
        final BreedDTO actualBreedDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BreedDTO.class);
        assertNotNull(actualBreedDto);
        assertEquals(breedDTO.getName(), actualBreedDto.getName());
    }

    private static final List<BreedDTO> BREED_DTO_LIST = ImmutableList.of(
            new BreedDTO(),
            new BreedDTO()
    );
    private BreedDTO defaultBreedDto() {
        return new BreedDTO().name("labrador");
    }
}
