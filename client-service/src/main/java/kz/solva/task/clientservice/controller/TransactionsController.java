package kz.solva.task.clientservice.controller;

import kz.solva.task.clientservice.service.transactions.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TransactionsController {
    private final TransactionsService transactionsService;
    @GetMapping("/get-over-limits")
    public List<Map<String, Object>> getOverLimits() {
        return transactionsService.getTransactionsOverLimit();
    }
}
