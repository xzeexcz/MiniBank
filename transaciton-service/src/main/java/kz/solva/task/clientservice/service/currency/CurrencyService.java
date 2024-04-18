package kz.solva.task.clientservice.service.currency;

import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

public interface CurrencyService {
    ResponseEntity<? extends Object> addCurrency() throws ExecutionException, InterruptedException;
}
