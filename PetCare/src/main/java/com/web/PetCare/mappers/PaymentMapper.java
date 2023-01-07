package com.web.PetCare.mappers;

import com.web.PetCare.dtos.PaymentDTO;
import com.web.PetCare.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", uses = {SessionMapper.class, OwnerMapper.class})
public interface PaymentMapper {

    @Mapping(target = "paymentDate", source = "paymentDTO", qualifiedByName = "paymentDateToLocalDateTime")
    Payment paymentDtoToPayment(PaymentDTO paymentDTO);

    @Named("paymentDateToLocalDateTime")
    default LocalDateTime mapPaymentDateToLocalDateTime(PaymentDTO paymentDTO) {
        return paymentDTO.getPaymentDate().toLocalDateTime();
    }

    @Mapping(target = "paymentDate", source = "payment", qualifiedByName = "paymentDateToOffsetDateTime")
    PaymentDTO paymentToPaymentDto(Payment payment);

    @Named("paymentDateToOffsetDateTime")
    default OffsetDateTime mapPaymentDateToOffsetDateTime(Payment payment) {
        return payment.getPaymentDate().atOffset(ZoneOffset.UTC);
    }
}
