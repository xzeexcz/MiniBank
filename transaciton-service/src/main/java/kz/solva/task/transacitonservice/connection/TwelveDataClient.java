package kz.solva.task.transacitonservice.connection;

import kz.solva.task.transacitonservice.dto.TwelveDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class TwelveDataClient {
    private final String basicUrl = "https://api.twelvedata.com";
    private final String apiKey = "0c904e7b9e9449de9e5120bcfdd3c151";
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;
    private final Logger logger = LoggerFactory.getLogger(TwelveDataResponse.class);

    public TwelveDataClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.executorService = Executors.newFixedThreadPool(2);

    }

    public List<TwelveDataResponse> getQuotes() throws ExecutionException, InterruptedException {
        List<Future<TwelveDataResponse>> quotes = new ArrayList<>();
        quotes.add(executorService.submit(() -> getQuote("USD/KZT")));
        quotes.add(executorService.submit(() -> getQuote("RUB/USD")));

        List<TwelveDataResponse> responses = new ArrayList<>();
        for(Future<TwelveDataResponse> quote : quotes) {
            responses.add(quote.get());
        }
        return responses;
    }

    @NewSpan("getQuote")
    private TwelveDataResponse getQuote(String symbol) {
        logger.info("Отправляю запрос на получение курса валют для пары :{}" , symbol);
        String url = basicUrl + "/quote?symbol=" + symbol + "&apikey=" + apiKey;
        logger.info("Успешно получены курсы валют для пары:{}", symbol);
        return restTemplate.getForObject(url, TwelveDataResponse.class);
    }
}
