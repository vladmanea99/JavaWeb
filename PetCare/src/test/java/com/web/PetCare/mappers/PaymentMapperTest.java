package com.web.PetCare.mappers;

import com.web.PetCare.dtos.*;
import com.web.PetCare.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PaymentMapperImpl.class, SessionMapperImpl.class, PetMapperImpl.class, BreedMapperImpl.class, OwnerMapperImpl.class, TreatmentMapperImpl.class})
public class PaymentMapperTest {

    @Autowired
    PaymentMapper paymentMapper;

    @Test
    public void paymentToPaymentDtoTest() {
        final Payment payment = new Payment(
                1L,
                LocalDateTime.now(),
                100,
                new Session(
                1L,
                LocalDateTime.now(),
                new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                new Treatment(29L, "maggots removal", null)),
                new Owner(5L, "Vlad", "Manea")
        );

        PaymentDTO actualPaymentDto = paymentMapper.paymentToPaymentDto(payment);

        assertEquals(payment.getId(), actualPaymentDto.getId());
        assertEquals(payment.getAmount(), actualPaymentDto.getAmount());
        assertEquals(payment.getPaymentDate(), actualPaymentDto.getPaymentDate().toLocalDateTime());
        assertEquals(payment.getSession().getSessionDate(), actualPaymentDto.getSession().getSessionDate().toLocalDateTime());
        assertEquals(payment.getSession().getTreatment().getId(), actualPaymentDto.getSession().getTreatment().getId());
        assertEquals(payment.getSession().getTreatment().getName(), actualPaymentDto.getSession().getTreatment().getName());
        assertEquals(payment.getSession().getTreatment().getDescription(), actualPaymentDto.getSession().getTreatment().getDescription());
        assertEquals(payment.getSession().getPet().getId(), actualPaymentDto.getSession().getPet().getId());
        assertEquals(payment.getSession().getPet().getName(), actualPaymentDto.getSession().getPet().getName());
        assertEquals(payment.getSession().getPet().getBreed().getId(), actualPaymentDto.getSession().getPet().getBreed().getId());
        assertEquals(payment.getSession().getPet().getBreed().getName(), actualPaymentDto.getSession().getPet().getBreed().getName());
        assertEquals(payment.getSession().getPet().getBreed().getDescription(), actualPaymentDto.getSession().getPet().getBreed().getDescription());
        assertEquals(payment.getSession().getPet().getOwner().getId(), actualPaymentDto.getSession().getPet().getOwner().getId());
        assertEquals(payment.getSession().getPet().getOwner().getFirstName(), actualPaymentDto.getSession().getPet().getOwner().getFirstName());
        assertEquals(payment.getSession().getPet().getOwner().getLastName(), actualPaymentDto.getSession().getPet().getOwner().getLastName());
        assertEquals(payment.getOwner().getId(), actualPaymentDto.getOwner().getId());
        assertEquals(payment.getOwner().getFirstName(), actualPaymentDto.getOwner().getFirstName());
        assertEquals(payment.getOwner().getLastName(), actualPaymentDto.getOwner().getLastName());

    }

    @Test
    public void paymentDtoToPaymentTest() {
        final PaymentDTO paymentDTO = new PaymentDTO()
                .id(1L)
                .paymentDate(OffsetDateTime.now())
                .amount(100)
                .session(new SessionDTO()
                        .id(1L)
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

        final Payment actualPayment = paymentMapper.paymentDtoToPayment(paymentDTO);

        assertEquals(paymentDTO.getId(), actualPayment.getId());
        assertEquals(paymentDTO.getPaymentDate().toLocalDateTime(), actualPayment.getPaymentDate());
        assertEquals(paymentDTO.getAmount(), actualPayment.getAmount());
        assertEquals(paymentDTO.getSession().getSessionDate().toLocalDateTime(), actualPayment.getSession().getSessionDate());
        assertEquals(paymentDTO.getSession().getTreatment().getId(), actualPayment.getSession().getTreatment().getId());
        assertEquals(paymentDTO.getSession().getTreatment().getName(), actualPayment.getSession().getTreatment().getName());
        assertEquals(paymentDTO.getSession().getTreatment().getDescription(), actualPayment.getSession().getTreatment().getDescription());
        assertEquals(paymentDTO.getSession().getPet().getId(), actualPayment.getSession().getPet().getId());
        assertEquals(paymentDTO.getSession().getPet().getName(), actualPayment.getSession().getPet().getName());
        assertEquals(paymentDTO.getSession().getPet().getBreed().getId(), actualPayment.getSession().getPet().getBreed().getId());
        assertEquals(paymentDTO.getSession().getPet().getBreed().getName(), actualPayment.getSession().getPet().getBreed().getName());
        assertEquals(paymentDTO.getSession().getPet().getBreed().getDescription(), actualPayment.getSession().getPet().getBreed().getDescription());
        assertEquals(paymentDTO.getSession().getPet().getOwner().getId(), actualPayment.getSession().getPet().getOwner().getId());
        assertEquals(paymentDTO.getSession().getPet().getOwner().getFirstName(), actualPayment.getSession().getPet().getOwner().getFirstName());
        assertEquals(paymentDTO.getSession().getPet().getOwner().getLastName(), actualPayment.getSession().getPet().getOwner().getLastName());
        assertEquals(paymentDTO.getOwner().getId(), actualPayment.getOwner().getId());
        assertEquals(paymentDTO.getOwner().getFirstName(), actualPayment.getOwner().getFirstName());
        assertEquals(paymentDTO.getOwner().getLastName(), actualPayment.getOwner().getLastName());

    }

}
