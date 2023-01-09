package com.web.PetCare.controllers;

import com.web.PetCare.dtos.PaymentDTO;
import com.web.PetCare.services.PaymentService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-01-07T11:51:59.339798200+02:00[Europe/Bucharest]")
@Controller
@RequestMapping("${openapi.petCareService.base-path:}")
public class PaymentsApiController implements PaymentsApi {

    private final NativeWebRequest request;

    private final PaymentService paymentService;

    @org.springframework.beans.factory.annotation.Autowired
    public PaymentsApiController(NativeWebRequest request, PaymentService paymentService) {
        this.request = request;
        this.paymentService = paymentService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<PaymentDTO>> getPayments() {
        List<PaymentDTO> paymentDTOS = paymentService.getPayments();
        return ResponseEntity.status(HttpStatus.OK).body(paymentDTOS);
    }

    @Override
    public ResponseEntity<PaymentDTO> createPayment(@Valid PaymentDTO paymentDTO) {
        PaymentDTO savedPaymentDto = paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(savedPaymentDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<String> getTotalPaymentPet(@PathVariable("petId") Long petId) {
        String paidAmount = paymentService.getPayedAmountPerPet(petId);
        return new ResponseEntity<>(paidAmount, HttpStatus.OK);
    }

}
