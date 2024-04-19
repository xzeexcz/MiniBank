package kz.solva.task.transactionsservice.controller.payment;


import kz.solva.task.transactionsservice.dto.payment.PaymentDto;
import kz.solva.task.transactionsservice.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/register-transaction")
    public ResponseEntity<? extends Object> registerTransaction(@RequestBody PaymentDto paymentDto) {
        return paymentService.makePayment(paymentDto);
    }
}
