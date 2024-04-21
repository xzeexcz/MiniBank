package kz.solva.task.clientservice.utils.transactions;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${api.gateway.basic.url}")
    private String BASIC_URL;

    @Value("${over.limit.endpoint}")
    private String ENDPOINT_URL;

    RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> getTransactionsOverLimit() {
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                BASIC_URL + ENDPOINT_URL ,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        return response.getBody();
    }
}
