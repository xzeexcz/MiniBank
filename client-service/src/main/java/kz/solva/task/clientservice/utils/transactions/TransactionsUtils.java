package kz.solva.task.clientservice.utils.transactions;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public final class TransactionsUtils {
    private TransactionsUtils() {}
    private final static String BASIC_URL = "http://localhost:8888/transactions";
    RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> getTransactionsOverLimit() {
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                BASIC_URL + "/get-over-transactions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        return response.getBody();
    }
}
