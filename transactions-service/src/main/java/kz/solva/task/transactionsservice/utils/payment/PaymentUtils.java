package kz.solva.task.transactionsservice.utils.payment;

import kz.solva.task.transactionsservice.entity.enums.CurrencyShortname;
import kz.solva.task.transactionsservice.utils.limit.LimitUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentUtils {
    private final LimitUtils limitUtils;

    public boolean isBiggerThanLimit(double paymentSum, double limitSum, CurrencyShortname currencyShortname) {
        switch (currencyShortname) {
            case KZT, RUB, USD -> {
                double total = limitUtils.doConvertToDollar(paymentSum, currencyShortname);
                if (total > limitSum) {
                    return true;
                } else {
                    return false;
                }
            }
            default -> {
                return false;
            }
        }
    }
}
