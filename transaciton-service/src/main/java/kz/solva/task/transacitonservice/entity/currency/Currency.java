package kz.solva.task.transacitonservice.entity.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@Table("t_currency")
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @PrimaryKey
    private Long id;
    private String symbol;
    private String name;
    private String dateTime;
    private double close;
    private double previous_close;
}