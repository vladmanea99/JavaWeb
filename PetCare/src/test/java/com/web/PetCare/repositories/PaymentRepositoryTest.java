package com.web.PetCare.repositories;

import com.web.PetCare.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaymentRepositoryTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void getAllPaymentsTest() {
        List<Payment> payments = paymentRepository.findAll();
        assertTrue(payments.size() > 0);
    }

    @Test
    public void createPaymentTest() {
        Payment payment = new Payment(
                null,
                LocalDateTime.now(),
                100,
                new Session(
                        null,
                        LocalDateTime.now(),
                        new Pet(1L, "Nitro", new Breed(34L, "rottweiler", "Very friendly breed"), new Owner(5L, "Vlad", "Manea")),
                        new Treatment(29L, "maggots removal", null)
                ),
                new Owner(5L, "Vlad", "Manea")
        );
        Payment savedPayment = paymentRepository.saveAndFlush(payment);
        assertNotNull(savedPayment);
        assertNotNull(savedPayment.getId());
        assertEquals(payment.getPaymentDate(), savedPayment.getPaymentDate());
        assertEquals(payment.getSession().getId(), savedPayment.getSession().getId());
        assertEquals(payment.getOwner().getId(), savedPayment.getOwner().getId());
    }

}
