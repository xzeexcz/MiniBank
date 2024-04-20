package kz.solva.task.transactionsservice.controller.payment;


import kz.solva.task.transactionsservice.dto.payment.PaymentDto;
import kz.solva.task.transactionsservice.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/register-transaction")
    public ResponseEntity<? extends Object> registerTransaction(@RequestBody PaymentDto paymentDto) {
        return paymentService.makePayment(paymentDto);
    }

    @GetMapping("/get-over-transactions")
    public List<Map<String, Object>> overLimitTransactions() {
        return paymentService.findPaymentsWithLimitExceeded();
    }
}
