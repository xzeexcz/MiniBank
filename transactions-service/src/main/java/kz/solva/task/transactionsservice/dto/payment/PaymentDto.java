package kz.solva.task.transactionsservice.dto.payment;

import kz.solva.task.transactionsservice.entity.enums.CurrencyShortname;
import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
public class PaymentDto {
    private BigDecimal account_from;
    private BigDecimal account_to;
    private double sum;
    private CurrencyShortname currencyShortname;
    private ExpenseCategory expenseCategory;
}
