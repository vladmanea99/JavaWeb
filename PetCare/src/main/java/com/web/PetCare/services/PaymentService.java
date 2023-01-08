package com.web.PetCare.services;

import com.web.PetCare.dtos.PaymentDTO;
import com.web.PetCare.mappers.PaymentMapper;
import com.web.PetCare.models.Payment;
import com.web.PetCare.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    PaymentRepository paymentRepository;

    PaymentMapper paymentMapper;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public List<PaymentDTO> getPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::paymentToPaymentDto)
                .collect(Collectors.toList());
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment savedPayment = paymentRepository.saveAndFlush(paymentMapper.paymentDtoToPayment(paymentDTO));
        return paymentMapper.paymentToPaymentDto(savedPayment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
