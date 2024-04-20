package kz.solva.task.transactionsservice.service.payment;

import kz.solva.task.transactionsservice.api.ClientLimitRequest;
import kz.solva.task.transactionsservice.dto.payment.PaymentDto;
import kz.solva.task.transactionsservice.entity.enums.CurrencyShortname;
import org.springframework.http.ResponseEntity;


public interface PaymentService {
    ResponseEntity<? extends Object> makePayment(PaymentDto paymentDto);
    boolean setFlag(double paymentSum, double limitSum, CurrencyShortname currencyShortname);

    ResponseEntity<?> setNewLimit(ClientLimitRequest clientLimitRequest);

}
