package kz.solva.task.transacitonservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwelveDataResponse {
    private String symbol;
    private String name;
    private String dateTime;
    private double close;
    private double previous_close;

    public boolean isValid() {
        return symbol != null && name != null && dateTime != null && previous_close < 0;
    }
}
