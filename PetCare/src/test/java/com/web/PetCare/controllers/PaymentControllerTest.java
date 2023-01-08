package com.web.PetCare.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.*;
import com.web.PetCare.services.PaymentService;
import com.web.PetCare.services.SessionService;
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

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentsApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void getListOfPaymentsTest() throws Exception {
        when(paymentService.getPayments()).thenReturn(PAYMENTS_DTO_LIST);

        final MvcResult mvcResult = mockMvc.perform(get("/payments/payment"))
                .andExpect(status().isOk())
                .andReturn();

        final List<PaymentDTO> actualPaymentDtoList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PaymentDTO[].class));
        assertNotNull(actualPaymentDtoList);
        assertEquals(2, actualPaymentDtoList.size());
        verify(paymentService, times(1)).getPayments();
    }

    @Test
    public void createSessionTest() throws Exception {
        PaymentDTO paymentDTO = defaultPaymentDto();
        when(paymentService.createPayment(any())).thenReturn(paymentDTO);

        String paymentDtoStr = objectMapper.writeValueAsString(paymentDTO);

        RequestBuilder postEvent = post("/payments/payment")
                .accept(MediaType.APPLICATION_JSON)
                .content(paymentDtoStr)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(postEvent).andExpect(status().isCreated()).andReturn();
        assertNotNull(mvcResult);
        verify(paymentService, times(1)).createPayment(any());
        final PaymentDTO actualPaymentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PaymentDTO.class);
        assertNotNull(actualPaymentDto);
        assertEquals(paymentDTO.getPaymentDate().atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime(), actualPaymentDto.getPaymentDate());
        assertEquals(paymentDTO.getAmount(), actualPaymentDto.getAmount());
        assertEquals(paymentDTO.getSession().getSessionDate().atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime(), actualPaymentDto.getSession().getSessionDate());
        assertEquals(paymentDTO.getSession().getTreatment().getId(), actualPaymentDto.getSession().getTreatment().getId());
        assertEquals(paymentDTO.getSession().getTreatment().getName(), actualPaymentDto.getSession().getTreatment().getName());
        assertEquals(paymentDTO.getSession().getTreatment().getDescription(), actualPaymentDto.getSession().getTreatment().getDescription());
        assertEquals(paymentDTO.getSession().getPet().getId(), actualPaymentDto.getSession().getPet().getId());
        assertEquals(paymentDTO.getSession().getPet().getName(), actualPaymentDto.getSession().getPet().getName());
        assertEquals(paymentDTO.getOwner().getId(), actualPaymentDto.getOwner().getId());
        assertEquals(paymentDTO.getOwner().getFirstName(), actualPaymentDto.getOwner().getFirstName());
    }

    @Test
    public void deletePaymentTest() throws Exception {
        doNothing().when(paymentService).deletePayment(any());
        RequestBuilder deleteEvent = delete("/payments/payment/100");
        mockMvc.perform(deleteEvent).andExpect(status().isNoContent());
        verify(paymentService, times(1)).deletePayment(any());
    }

    private PaymentDTO defaultPaymentDto() {
        return new PaymentDTO()
                .id(1L)
                .paymentDate(OffsetDateTime.now())
                .amount(100)
                .session(new SessionDTO()
                        .sessionDate(OffsetDateTime.now().minusHours(2))
                        .pet(new PetDTO()
                                .id(1L)
                                .name("Aron")
                                .breed(new BreedDTO().id(1L).name("rottweiler").description("best breed"))
                                .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea")))
                        .treatment(new TreatmentDTO()
                                .id(1L)
                                .name("massage")
                                .description(null)))
                .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea"));
    }

    private static final List<PaymentDTO> PAYMENTS_DTO_LIST = ImmutableList.of(
            new PaymentDTO(),
            new PaymentDTO()
    );

}
