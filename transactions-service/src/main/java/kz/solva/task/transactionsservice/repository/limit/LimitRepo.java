package kz.solva.task.transactionsservice.repository.limit;

import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import kz.solva.task.transactionsservice.entity.limit.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface LimitRepo extends JpaRepository<Limit, UUID> {
    @Query("SELECT l FROM Limit l WHERE l.account = :account AND l.expenseCategory = :category " +
            "AND l.dateTime = (SELECT MAX(l2.dateTime) FROM Limit l2 WHERE l2.account = :account AND l2.expenseCategory = :category)")
    Limit findRecentLimits3(@Param("account") BigDecimal account, @Param("category") ExpenseCategory category);
}
