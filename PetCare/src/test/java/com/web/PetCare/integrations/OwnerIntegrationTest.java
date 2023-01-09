package com.web.PetCare.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.PetCare.PetCareApplication;
import com.web.PetCare.dtos.OwnerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PetCareApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerIntegrationTest {
    private String host = "http://localhost";

    private static final String REQUEST_PATH_GET_OWNERS = "%s/owners/owner";
    private static final String REQUEST_PATH_CREATE_OWNER = "%s/owners/owner";

    private static final String REQUEST_PATH_OWNERS_THAT_PAID = "%s/owners/ownersThatPaid";
    private static final String REQUEST_PATH_DELETE_OWNER = "%s/owners/owner/%s";

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
        OwnerDTO ownerDTO = defaultOwnerDto();
        String ownerDtoStr = objectMapper.writeValueAsString(ownerDTO);
        RequestBuilder createOwner = post(String.format(REQUEST_PATH_CREATE_OWNER, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(ownerDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createOwner).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final OwnerDTO actualOwnerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OwnerDTO.class);
        assertNotNull(actualOwnerDto);
        assertEquals(ownerDTO.getFirstName(), actualOwnerDto.getFirstName());
        assertEquals(ownerDTO.getLastName(), actualOwnerDto.getLastName());

        RequestBuilder getAllOwners = get(String.format(REQUEST_PATH_GET_OWNERS, baseUri));
        final MvcResult mvcResult2 = mockMvc.perform(getAllOwners).andExpect(status().isOk()).andReturn();
        final List<OwnerDTO> actualOwnerDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), OwnerDTO[].class));

        assertFalse(actualOwnerDtoList.isEmpty());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_OWNER, baseUri, actualOwnerDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldCreateOwner() throws Exception {
        OwnerDTO ownerDTO = defaultOwnerDto();
        String ownerDtoStr = objectMapper.writeValueAsString(ownerDTO);
        RequestBuilder createOwner = post(String.format(REQUEST_PATH_CREATE_OWNER, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(ownerDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createOwner).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final OwnerDTO actualOwnerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OwnerDTO.class);
        assertNotNull(actualOwnerDto);
        assertEquals(ownerDTO.getFirstName(), actualOwnerDto.getFirstName());
        assertEquals(ownerDTO.getLastName(), actualOwnerDto.getLastName());
        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_OWNER, baseUri, actualOwnerDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldDeleteOwner() throws Exception {
        OwnerDTO ownerDTO = defaultOwnerDto();
        String ownerDtoStr = objectMapper.writeValueAsString(ownerDTO);
        RequestBuilder createOwner = post(String.format(REQUEST_PATH_CREATE_OWNER, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(ownerDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createOwner).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final OwnerDTO actualOwnerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OwnerDTO.class);
        assertNotNull(actualOwnerDto);
        assertEquals(ownerDTO.getFirstName(), actualOwnerDto.getFirstName());
        assertEquals(ownerDTO.getLastName(), actualOwnerDto.getLastName());
        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_OWNER, baseUri, actualOwnerDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetOwnersThatPaid() throws Exception {
        RequestBuilder getAllOwnersThatPaid = get(String.format(REQUEST_PATH_OWNERS_THAT_PAID, baseUri));
        final MvcResult mvcResult2 = mockMvc.perform(getAllOwnersThatPaid).andExpect(status().isOk()).andReturn();
        final List<OwnerDTO> actualOwnerDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), OwnerDTO[].class));

    }

    private OwnerDTO defaultOwnerDto() {
        return new OwnerDTO().firstName("John").lastName("Snow");
    }
}
