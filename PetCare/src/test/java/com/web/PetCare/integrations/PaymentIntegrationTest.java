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

import java.text.DateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PetCareApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentIntegrationTest {

    private String host = "http://localhost";

    private static final String REQUEST_PATH_GET_PAYMENTS = "%s/payments/payment";
    private static final String REQUEST_PATH_CREATE_PAYMENT = "%s/payments/payment";

    private static final String REQUEST_PATH_GET_PAYMENT_PER_PET = "%s/payments/petPayment/%s";

    private static final String REQUEST_PATH_DELETE_PAYMENT = "%s/payments/payment/%s";

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
    public void shouldGetAllPayments() throws Exception {
        PaymentDTO paymentDTO = defaultPaymentDto();
        String paymentDtoStr = objectMapper.writeValueAsString(paymentDTO);
        RequestBuilder createPayment = post(String.format(REQUEST_PATH_CREATE_PAYMENT, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(paymentDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createPayment).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final PaymentDTO actualPaymentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PaymentDTO.class);
        assertNotNull(actualPaymentDto);
        assertEquals(paymentDTO.getPaymentDate().atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime(), actualPaymentDto.getPaymentDate());
        assertEquals(paymentDTO.getAmount(), actualPaymentDto.getAmount());
        assertEquals(paymentDTO.getSession().getPet().getName(), actualPaymentDto.getSession().getPet().getName());
        assertEquals(paymentDTO.getOwner().getFirstName(), actualPaymentDto.getOwner().getFirstName());

        RequestBuilder getAllPayments = get(String.format(REQUEST_PATH_GET_PAYMENTS, baseUri));
        final MvcResult mvcResult2 = mockMvc.perform(getAllPayments).andExpect(status().isOk()).andReturn();
        final List<PaymentDTO> actualPaymentDtoList = Arrays.asList(objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), PaymentDTO[].class));

        assertFalse(actualPaymentDtoList.isEmpty());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_PAYMENT, baseUri, actualPaymentDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldCreateOwner() throws Exception {
        PaymentDTO paymentDTO = defaultPaymentDto();
        String paymentDtoStr = objectMapper.writeValueAsString(paymentDTO);
        RequestBuilder createPayment = post(String.format(REQUEST_PATH_CREATE_PAYMENT, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(paymentDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createPayment).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final PaymentDTO actualPaymentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PaymentDTO.class);
        assertNotNull(actualPaymentDto);
        assertEquals(paymentDTO.getPaymentDate().atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime(), actualPaymentDto.getPaymentDate());
        assertEquals(paymentDTO.getAmount(), actualPaymentDto.getAmount());
        assertEquals(paymentDTO.getSession().getPet().getName(), actualPaymentDto.getSession().getPet().getName());
        assertEquals(paymentDTO.getOwner().getFirstName(), actualPaymentDto.getOwner().getFirstName());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_PAYMENT, baseUri, actualPaymentDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());

    }

    @Test
    public void shouldDeleteOwner() throws Exception {
        PaymentDTO paymentDTO = defaultPaymentDto();
        String paymentDtoStr = objectMapper.writeValueAsString(paymentDTO);
        RequestBuilder createPayment = post(String.format(REQUEST_PATH_CREATE_PAYMENT, baseUri))
                .accept(MediaType.APPLICATION_JSON)
                .content(paymentDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(createPayment).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        final PaymentDTO actualPaymentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PaymentDTO.class);
        assertNotNull(actualPaymentDto);
        assertEquals(paymentDTO.getPaymentDate().atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime(), actualPaymentDto.getPaymentDate());
        assertEquals(paymentDTO.getAmount(), actualPaymentDto.getAmount());
        assertEquals(paymentDTO.getSession().getPet().getName(), actualPaymentDto.getSession().getPet().getName());
        assertEquals(paymentDTO.getOwner().getFirstName(), actualPaymentDto.getOwner().getFirstName());

        RequestBuilder deleteEvent = delete(String.format(REQUEST_PATH_DELETE_PAYMENT, baseUri, actualPaymentDto.getId()));
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetPaidAmountPerPet() throws Exception {
        RequestBuilder getAllPayments = get(String.format(REQUEST_PATH_GET_PAYMENT_PER_PET, baseUri, "1"));
        final MvcResult mvcResult2 = mockMvc.perform(getAllPayments).andExpect(status().isOk()).andReturn();
        final Integer amount = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), Integer.class);

    }

    @Test
    public void shouldThrowErrorWhenPetDoesntExist() throws Exception {
        RequestBuilder getAllPayments = get(String.format(REQUEST_PATH_GET_PAYMENT_PER_PET, baseUri, "-1"));
        mockMvc.perform(getAllPayments).andExpect(status().isNotFound());
    }

    private PaymentDTO defaultPaymentDto() {
        return new PaymentDTO()
                .paymentDate(OffsetDateTime.now())
                .amount(100)
                .session(new SessionDTO()
                        .id(1L)
                        .sessionDate(OffsetDateTime.of(2022, 12, 28, 16, 18, 1, 0, ZoneOffset.UTC))
                        .pet(new PetDTO()
                                .id(1L)
                                .name("Aron")
                                .breed(new BreedDTO().id(34L).name("rottweiler").description("best breed"))
                                .owner(new OwnerDTO().id(5L).firstName("Vlad").lastName("Manea")))
                        .treatment(new TreatmentDTO()
                                .id(29L)
                                .name("massage")
                                .description("bath + massage of the fur and body of the pet")))
                .owner(new OwnerDTO().id(5L).firstName("Vlad").lastName("Manea"));
    }

}
