package kz.solva.task.transactionsservice.mapper.payment;

import kz.solva.task.transactionsservice.dto.payment.PaymentDto;
import kz.solva.task.transactionsservice.entity.payment.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toPaymentEntity(PaymentDto paymentDto);
}
