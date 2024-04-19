package kz.solva.task.transactionsservice.dto.limit;

import kz.solva.task.transactionsservice.entity.enums.CurrencyShortname;
import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LimitDto {

    private BigDecimal account;
    private double limitSum;
    private ExpenseCategory expenseCategory;

}
