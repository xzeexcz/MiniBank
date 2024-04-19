package kz.solva.task.transactionsservice.repository.limit;

import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import kz.solva.task.transactionsservice.entity.limit.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface LimitRepo extends JpaRepository<Limit, UUID> {
    Limit findFirstByAccountAndExpenseCategoryEqualsOrderByDateTimeDesc(BigDecimal account, ExpenseCategory expenseCategory);
}
