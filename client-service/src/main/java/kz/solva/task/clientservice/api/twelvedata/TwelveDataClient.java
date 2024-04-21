package kz.solva.task.clientservice.api.twelvedata;

import kz.solva.task.clientservice.dto.twelvedata.TwelveDataResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
@Slf4j
@ComponentScan(basePackages = {"kz.solva.task.clientservice"})
// todo ### вешаu @RequiredArgsConstructor и все final поля (нестатические станут бинами), а конструктор можешь удалить
public class TwelveDataClient {

    @Value("${kz.solva.twelvedata.url}")
    private String basicUrl;

    @Value("${kz.solva.twelvedata.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    public static final String USD_KZT = "USD/KZT";
    public static final String RUB_USD = "RUB/USD";



    public List<TwelveDataResponse> getQuotes() throws ExecutionException, InterruptedException { // метод для получения курсов валют
        List<Future<TwelveDataResponse>> quotes = new ArrayList<>();

        quotes.add(executorService.submit(() -> getQuote(USD_KZT)));
        quotes.add(executorService.submit(() -> getQuote(RUB_USD)));

        List<TwelveDataResponse> responses = new ArrayList<>();
        for(Future<TwelveDataResponse> quote : quotes) {
            responses.add(quote.get());
        }
        return responses;
    }

    @NewSpan("getQuote")
    private TwelveDataResponse getQuote(String symbol) throws RestClientException { // метод для полючения курса одной пары валют
        try {
            log.info("Отправляю запрос на получение курса валют для пары :{}" , symbol);
            String url = basicUrl + "/quote?symbol=" + symbol + "&apikey=" + apiKey;
            log.info("Успешно получены курсы валют для пары:{}", symbol);
            return restTemplate.getForObject(url, TwelveDataResponse.class);
        } catch (RestClientException e) {
            log.atWarn().log("Что-то пошло не так", e);
            throw e;
        }
    }
}
