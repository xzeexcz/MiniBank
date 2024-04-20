package kz.solva.task.transactionsservice.repository.payment;

import kz.solva.task.transactionsservice.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, UUID> {
    @Query(value = "SELECT p.*, l.limit_sum, l.limit_datetime AS limit_date_time, l.limit_currency_shortname " +
            "FROM t_payments p " +
            "JOIN t_limits l ON p.account_from = l.account " +
            "AND p.expense_category = l.expense_category " +
            "AND p.currency_shortname = l.limit_currency_shortname " +
            "WHERE p.limit_exceded = true", nativeQuery = true)
    List<Map<String, Object>> findPaymentsWithLimitExceeded();
}
