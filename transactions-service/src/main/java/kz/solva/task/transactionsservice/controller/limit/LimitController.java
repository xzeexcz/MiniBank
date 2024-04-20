package kz.solva.task.transactionsservice.controller.limit;

import kz.solva.task.transactionsservice.api.ClientLimitRequest;
import kz.solva.task.transactionsservice.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitController {
    private final PaymentService paymentService;

    @PostMapping("/set-new-limit")
    public ResponseEntity<?> setNewLimit(@RequestBody ClientLimitRequest clientRequest) {
        return paymentService.setNewLimit(clientRequest);
    }
}
