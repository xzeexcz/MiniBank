package kz.solva.task.clientservice.service.currency;

import kz.solva.task.clientservice.dto.currency.CurrencyDto;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

public interface CurrencyService {
    ResponseEntity<? extends Object> addCurrency() throws ExecutionException, InterruptedException;
    CurrencyDto getCurrencyKzt() throws ExecutionException, InterruptedException;
    CurrencyDto getCurrencyRub() throws ExecutionException, InterruptedException;
    CurrencyDto getCurrencyPair(String currencyPair) throws ExecutionException, InterruptedException;
}
