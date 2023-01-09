package com.web.PetCare.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.OwnerDTO;
import com.web.PetCare.services.OwnerService;
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
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OwnersApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OwnerService ownerService;

    @Test
    public void getListOfOwners() throws Exception {
        when(ownerService.getOwners()).thenReturn(OWNER_DTO_LIST);

        final MvcResult mvcResult = mockMvc.perform(get("/owners/owner"))
                .andExpect(status().isOk())
                .andReturn();

        final List<OwnerDTO> actualOwnerDtoList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OwnerDTO[].class));
        assertNotNull(actualOwnerDtoList);
        assertEquals(2,actualOwnerDtoList.size());
        verify(ownerService, times(1)).getOwners();
    }

    @Test
    public void createOwnerTest() throws Exception {
        OwnerDTO ownerDTO = defaultOwnerDto();
        when(ownerService.createOwner(any())).thenReturn(ownerDTO);

        String ownerDtoStr = objectMapper.writeValueAsString(ownerDTO);

        RequestBuilder postEvent = post("/owners/owner")
                .accept(MediaType.APPLICATION_JSON)
                .content(ownerDtoStr)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(postEvent).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        verify(ownerService, times(1)).createOwner(any());
        final OwnerDTO actualOwnerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OwnerDTO.class);
        assertNotNull(actualOwnerDto);
        assertEquals(ownerDTO.getFirstName(), actualOwnerDto.getFirstName());
        assertEquals(ownerDTO.getLastName(), actualOwnerDto.getLastName());
    }

    @Test
    public void createOwnerTestThrowsBadRequest() throws Exception {
        OwnerDTO ownerDTO = badOwnerDto();
        when(ownerService.createOwner(any())).thenReturn(ownerDTO);

        String ownerDtoStr = objectMapper.writeValueAsString(ownerDTO);

        RequestBuilder postEvent = post("/owners/owner")
                .accept(MediaType.APPLICATION_JSON)
                .content(ownerDtoStr)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(postEvent).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteOwnerTest() throws Exception {
        doNothing().when(ownerService).deleteOwner(any());
        RequestBuilder deleteEvent = delete("/owners/owner/100");
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
        verify(ownerService, times(1)).deleteOwner(any());
    }

    private static final List<OwnerDTO> OWNER_DTO_LIST = ImmutableList.of(
            new OwnerDTO(),
            new OwnerDTO()
    );

    @Test
    public void getOwnersThatPaidTest() throws Exception {
        when(ownerService.getOwnersThatPaid()).thenReturn(OWNER_DTO_LIST);

        final MvcResult mvcResult = mockMvc.perform(get("/owners/ownersThatPaid"))
                .andExpect(status().isOk())
                .andReturn();

        final List<OwnerDTO> actualOwnerDtoList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OwnerDTO[].class));
        assertNotNull(actualOwnerDtoList);
        assertEquals(2,actualOwnerDtoList.size());
        verify(ownerService, times(1)).getOwnersThatPaid();
    }

    private OwnerDTO defaultOwnerDto() {
        return new OwnerDTO().firstName("John").lastName("Doe");
    }
    private OwnerDTO badOwnerDto() {
        return new OwnerDTO().firstName("John");
    }
}
