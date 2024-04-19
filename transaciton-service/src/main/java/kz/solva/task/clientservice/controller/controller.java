package kz.solva.task.clientservice.controller;

import kz.solva.task.clientservice.dto.currency.CurrencyDto;
import kz.solva.task.clientservice.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class controller {

    private final CurrencyService currencyService;

    @PostMapping("/currency")
    public ResponseEntity<? extends Object> getCurrency() throws ExecutionException, InterruptedException {
        return currencyService.addCurrency();
    }

    @GetMapping("/currency-close")
    public CurrencyDto getCurrencyPair(@RequestParam(name = "symbol") String symbol) throws ExecutionException, InterruptedException {
        return currencyService.getCurrencyPair(symbol);
    }
}
