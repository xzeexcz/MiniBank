package kz.solva.task.clientservice.controller;

import kz.solva.task.clientservice.dto.currency.CurrencyDto;
import kz.solva.task.clientservice.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/get")
    public ResponseEntity<String> getCurrency() throws ExecutionException, InterruptedException {
        return currencyService.addCurrency();
    }

    @GetMapping("/get-currency-close")
    public CurrencyDto getCurrencyPair(@RequestParam(name = "symbol") String symbol) throws ExecutionException, InterruptedException {
        return currencyService.getCurrencyPair(symbol);
    }
}
