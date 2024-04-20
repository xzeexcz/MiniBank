package kz.solva.task.transactionsservice.api;

import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ClientLimitRequest {
    private BigDecimal account;
    private double limit;
    private ExpenseCategory expenseCategory;
}
