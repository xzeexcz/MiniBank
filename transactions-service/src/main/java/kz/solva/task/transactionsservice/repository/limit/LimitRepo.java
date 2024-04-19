package kz.solva.task.transactionsservice.repository.limit;

import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import kz.solva.task.transactionsservice.entity.limit.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public interface LimitRepo extends JpaRepository<Limit, UUID> {
    Limit findFirstByAccountAndExpenseCategoryEqualsOrderByDateTimeDesc(
            @Param("account") BigDecimal account,
            @Param("expenseCategory") ExpenseCategory expenseCategory);
    @Query("SELECT l FROM Limit l WHERE l.account = :account AND l.expenseCategory = :category AND l.dateTime >= :startDate ")
    Limit findRecentLimits(@Param("account") BigDecimal account, @Param("category") ExpenseCategory category, @Param("startDate") ZonedDateTime startDate);

    @Query("SELECT l FROM Limit l WHERE l.account = :account AND l.expenseCategory = :category AND " +
            "(l.dateTime >= :startDate OR NOT EXISTS (SELECT 1 FROM Limit l2 WHERE l2.account = :account AND l2.expenseCategory = :category AND l2.dateTime > :startDate))")
    Limit findRecentLimits2(@Param("account") BigDecimal account, @Param("category") ExpenseCategory category, @Param("startDate") ZonedDateTime startDate);
}
