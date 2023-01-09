package com.web.PetCare.services;

import com.google.common.collect.ImmutableList;
import com.web.PetCare.dtos.*;
import com.web.PetCare.mappers.PaymentMapper;
import com.web.PetCare.models.*;
import com.web.PetCare.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class PaymentServiceTest {

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private PaymentMapper paymentMapper;

    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        paymentService = new PaymentService(paymentRepository, paymentMapper);
    }

    @Test
    public void getAllPaymentsTest() {
        final List<PaymentDTO> paymentDTOList = PAYMENT_DTO_LIST;
        final List<Payment> paymentList = PAYMENT_LIST;

        when(paymentRepository.findAll()).thenReturn(paymentList);
        when(paymentMapper.paymentToPaymentDto(eq(paymentList.get(0)))).thenReturn(paymentDTOList.get(0));

        List<PaymentDTO> actualPaymentDtoList = paymentService.getPayments();

        assertNotNull(actualPaymentDtoList);

        assertEquals(paymentDTOList.get(0).getId(), actualPaymentDtoList.get(0).getId());
        assertEquals(paymentDTOList.get(0).getAmount(), actualPaymentDtoList.get(0).getAmount());
        assertEquals(paymentDTOList.get(0).getPaymentDate().toLocalDateTime(), actualPaymentDtoList.get(0).getPaymentDate().toLocalDateTime());
        assertEquals(paymentDTOList.get(0).getSession().getId(), actualPaymentDtoList.get(0).getSession().getId());
        assertEquals(paymentDTOList.get(0).getSession().getSessionDate().toLocalDateTime(), actualPaymentDtoList.get(0).getSession().getSessionDate().toLocalDateTime());
        assertEquals(paymentDTOList.get(0).getSession().getTreatment().getId(), actualPaymentDtoList.get(0).getSession().getTreatment().getId());
        assertEquals(paymentDTOList.get(0).getSession().getTreatment().getName(), actualPaymentDtoList.get(0).getSession().getTreatment().getName());
        assertEquals(paymentDTOList.get(0).getSession().getTreatment().getDescription(), actualPaymentDtoList.get(0).getSession().getTreatment().getDescription());
        assertEquals(paymentDTOList.get(0).getSession().getPet().getId(), actualPaymentDtoList.get(0).getSession().getPet().getId());
        assertEquals(paymentDTOList.get(0).getSession().getPet().getName(), actualPaymentDtoList.get(0).getSession().getPet().getName());
        assertEquals(paymentDTOList.get(0).getSession().getPet().getBreed().getId(), actualPaymentDtoList.get(0).getSession().getPet().getBreed().getId());
        assertEquals(paymentDTOList.get(0).getSession().getPet().getBreed().getName(), actualPaymentDtoList.get(0).getSession().getPet().getBreed().getName());
        assertEquals(paymentDTOList.get(0).getSession().getPet().getBreed().getDescription(), actualPaymentDtoList.get(0).getSession().getPet().getBreed().getDescription());
        assertEquals(paymentDTOList.get(0).getSession().getPet().getOwner().getId(), actualPaymentDtoList.get(0).getSession().getPet().getOwner().getId());
        assertEquals(paymentDTOList.get(0).getSession().getPet().getOwner().getFirstName(), actualPaymentDtoList.get(0).getSession().getPet().getOwner().getFirstName());
        assertEquals(paymentDTOList.get(0).getSession().getPet().getOwner().getLastName(), actualPaymentDtoList.get(0).getSession().getPet().getOwner().getLastName());
        assertEquals(paymentDTOList.get(0).getOwner().getId(), actualPaymentDtoList.get(0).getOwner().getId());
        assertEquals(paymentDTOList.get(0).getOwner().getFirstName(), actualPaymentDtoList.get(0).getOwner().getFirstName());
        assertEquals(paymentDTOList.get(0).getOwner().getLastName(), actualPaymentDtoList.get(0).getOwner().getLastName());

    }

    @Test
    public void createSessionTest() {
        final PaymentDTO entryPaymentDto = defaultEntryPaymentDto();
        final Payment entryPayment = defaultEntryPayment();

        final PaymentDTO exitPaymentDto = defaultExitPaymentDto();
        final Payment exitPayment = defaultExitPayment();

        when(paymentMapper.paymentDtoToPayment(any())).thenReturn(entryPayment);
        when(paymentRepository.saveAndFlush(any())).thenReturn(exitPayment);
        when(paymentMapper.paymentToPaymentDto(any())).thenReturn(exitPaymentDto);

        final PaymentDTO actualPaymentDto = paymentService.createPayment(entryPaymentDto);
        assertNotNull(actualPaymentDto);
        assertEquals(exitPaymentDto.getId(), actualPaymentDto.getId());
        assertEquals(exitPaymentDto.getAmount(), actualPaymentDto.getAmount());
        assertEquals(exitPaymentDto.getPaymentDate(), actualPaymentDto.getPaymentDate());
        assertEquals(exitPaymentDto.getSession().getSessionDate(), actualPaymentDto.getSession().getSessionDate());
        assertEquals(exitPaymentDto.getSession().getPet().getId(), actualPaymentDto.getSession().getPet().getId());
        assertEquals(exitPaymentDto.getSession().getPet().getName(), actualPaymentDto.getSession().getPet().getName());
        assertEquals(exitPaymentDto.getSession().getTreatment().getId(), actualPaymentDto.getSession().getTreatment().getId());
        assertEquals(exitPaymentDto.getSession().getTreatment().getName(), actualPaymentDto.getSession().getTreatment().getName());
        assertEquals(exitPaymentDto.getSession().getTreatment().getDescription(), actualPaymentDto.getSession().getTreatment().getDescription());
        assertEquals(exitPaymentDto.getOwner().getId(), actualPaymentDto.getOwner().getId());
        assertEquals(exitPaymentDto.getOwner().getFirstName(), actualPaymentDto.getOwner().getFirstName());
    }

    @Test
    public void deletePayment() {
        doNothing().when(paymentRepository).deleteById(any());
        paymentService.deletePayment(1L);
        verify(paymentRepository, times(1)).deleteById(any());
    }

    @Test
    public void getPaidAmountPerPetTest() {
        when(paymentRepository.getTotalAmountByPetId(any())).thenReturn(1000);
        String actualAmount = paymentService.getPayedAmountPerPet(1L);
        assertEquals("1000", actualAmount);
    }

    private static final List<PaymentDTO> PAYMENT_DTO_LIST = ImmutableList.of(
            new PaymentDTO().id(1L)
                            .amount(100)
                                    .paymentDate(OffsetDateTime.now())
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
                    .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea"))

    );

    private static final List<Payment> PAYMENT_LIST = ImmutableList.of(
            new Payment(2L, LocalDateTime.now(), 100, new Session(
                    1L,
                    LocalDateTime.now(),
                    new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                    new Treatment(29L, "maggots removal", null)
            ),
                    new Owner(5L, "Vlad", "Manea")
    ));

    private PaymentDTO defaultEntryPaymentDto() {
        return new PaymentDTO()
                .id(null)
                .paymentDate(OffsetDateTime.now())
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
                .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea"))
                .amount(100);
    }

    private PaymentDTO defaultExitPaymentDto() {
        return new PaymentDTO()
                .id(1L)
                .paymentDate(OffsetDateTime.now())
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
                .owner(new OwnerDTO().id(2L).firstName("Vlad").lastName("Manea"))
                .amount(100);

    }

    private Payment defaultExitPayment() {
        return new Payment(1L,
                LocalDateTime.now(),
                100,
                new Session(
                        1L,
                        LocalDateTime.now().minusHours(2),
                        new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                        new Treatment(29L, "maggots removal", null)

                ), new Owner(5L, "Vlad", "Manea"));
    }

    private Payment defaultEntryPayment() {
        return new Payment(null,
                LocalDateTime.now(),
                100,
                new Session(
                1L,
                LocalDateTime.now().minusHours(2),
                new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                new Treatment(29L, "maggots removal", null)

        ), new Owner(5L, "Vlad", "Manea"));
    }

}
