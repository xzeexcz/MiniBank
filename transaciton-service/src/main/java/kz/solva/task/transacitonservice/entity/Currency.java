package kz.solva.task.transacitonservice.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {
    private String symbol;
    private String name;
    private String dateTime;
    private double close;
    private double previous_close;
}
