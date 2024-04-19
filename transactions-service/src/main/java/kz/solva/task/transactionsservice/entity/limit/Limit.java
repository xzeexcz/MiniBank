package kz.solva.task.transactionsservice.entity.limit;

import jakarta.persistence.*;
import kz.solva.task.transactionsservice.entity.enums.CurrencyShortname;
import kz.solva.task.transactionsservice.entity.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_limits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "account")
    private BigDecimal account;

    @Column(name = "limit_sum")
    private double limitSum;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Column(name = "limit_currency_shortname")
    @Enumerated(EnumType.STRING)
    private CurrencyShortname currencyShortname;

    @Column(name = "limit_balance")
    private double limitBalance;

    @Column(name = "limit_datetime")
    private ZonedDateTime dateTime;
}
