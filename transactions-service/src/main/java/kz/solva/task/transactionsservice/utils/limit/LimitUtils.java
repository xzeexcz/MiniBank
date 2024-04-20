package kz.solva.task.transactionsservice.utils.limit;

import kz.solva.task.transactionsservice.api.ClientLimitRequest;
import kz.solva.task.transactionsservice.dto.currency.CurrencyDto;
import kz.solva.task.transactionsservice.entity.enums.CurrencyShortname;
import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public final class LimitUtils {
    private LimitUtils() {}
    private static final String Basic_URL = "http://localhost:8888/client";
    RestTemplate restTemplate = new RestTemplate();

    public CurrencyDto requestCurrencyPair(String currencyPair) {
        return restTemplate
                .getForObject(Basic_URL + "/currency-close?symbol=" + currencyPair, CurrencyDto.class);
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
                    convertedToDollar = PaymentSum * currencyDto.getPreviousClose();
                } else {
                    convertedToDollar = PaymentSum * currencyDto.getClose();
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

    public boolean checkData(ClientLimitRequest clientLimitRequest) {
            if (clientLimitRequest.getAccount() == null || clientLimitRequest.getExpenseCategory() == null) {
                return false;
            }

            double limit;
            try {
                limit = Double.parseDouble(String.valueOf(clientLimitRequest.getLimit()));
            } catch (NumberFormatException e) {
                return false;
            }
            if (limit < 1000) {
                return false;
            }

            ExpenseCategory expenseCategory = clientLimitRequest.getExpenseCategory();
            if (expenseCategory != ExpenseCategory.PRODUCT && expenseCategory != ExpenseCategory.SERVICE) {
                return false;
            }
            return true;
    }
}
