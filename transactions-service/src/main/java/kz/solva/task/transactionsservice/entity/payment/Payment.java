package kz.solva.task.transactionsservice.entity.payment;

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
@Table(name = "t_payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "account_from")
    private BigDecimal account_from;

    @Column(name = "account_to")
    private BigDecimal account_to;

    @Column(name = "sum")
    private Double sum;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_shortname")
    private CurrencyShortname currencyShortname;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category")
    private ExpenseCategory expenseCategory;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @Column(name = "limit_exceded")
    private boolean limit_exceded;
}
