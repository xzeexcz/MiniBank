package kz.solva.task.transactionsservice.utils.limit;

import kz.solva.task.transactionsservice.dto.currency.CurrencyDto;
import kz.solva.task.transactionsservice.entity.enums.CurrencyShortname;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LimitUtils {
    private static final String Basic_URL = "http://localhost:8888/client";
    RestTemplate restTemplate = new RestTemplate();

    public CurrencyDto requestCurrencyPair(String currencyPair) {
        CurrencyDto currencyDto = restTemplate
                .getForObject(Basic_URL + "/currency-close?symbol=" + currencyPair, CurrencyDto.class);
        return currencyDto;
    }

    public double doCalculateLimitBalance(double limitSum, double sum, CurrencyShortname currencyShortname) {
        double convertedToDollar;
        switch (currencyShortname) {
            case KZT, RUB -> {
                convertedToDollar = doConvertToDollar(sum, currencyShortname);
                return Math.round((limitSum - convertedToDollar) * 100.00) / 100.00;
            }
            case USD -> {
                return Math.round((limitSum - sum) * 100.00) / 100.00;
            }
            default -> {
                return convertedToDollar = 0.00;
            }
        }
    }

    public double doConvertToDollar(double PaymentSum, CurrencyShortname currencyShortname) {
        double convertedToDollar = 0;
        CurrencyDto currencyDto;
        switch (currencyShortname) {
            case KZT -> {
                currencyDto = requestCurrencyPair("USD/" + currencyShortname.toString());
                if (currencyDto != null || currencyDto.getClose() == 0) {
                    convertedToDollar = PaymentSum / currencyDto.getPreviousClose();
                } else {
                    convertedToDollar = PaymentSum / currencyDto.getClose();
                }
                return Math.round((convertedToDollar * 100.00) / 100.00);
            }
            case RUB -> {
                currencyDto = requestCurrencyPair(currencyShortname.toString() + "/USD");
                if (currencyDto != null || currencyDto.getClose() == 0) {
                    convertedToDollar = PaymentSum / currencyDto.getPreviousClose();
                } else {
                    convertedToDollar = PaymentSum / currencyDto.getClose();
                }
                return Math.round((convertedToDollar * 100.00) / 100.00);
            }
            case USD -> {
                return Math.round((PaymentSum * 100.00) / 100.00);
            }
            default -> {
                return convertedToDollar;
            }
        }
    }
}
