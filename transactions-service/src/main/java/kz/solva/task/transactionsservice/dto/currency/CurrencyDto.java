package kz.solva.task.transactionsservice.dto.currency;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDto {
    private double close;
    private double previousClose;
}
