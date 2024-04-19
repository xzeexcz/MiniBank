package kz.solva.task.transactionsservice.service.limit;

import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import kz.solva.task.transactionsservice.entity.limit.Limit;

import java.math.BigDecimal;

public interface LimitService {
    Limit isExists(BigDecimal account, ExpenseCategory expenseCategory);
//    void setNewLimit(BigDecimal account, String category, double sum);
}
