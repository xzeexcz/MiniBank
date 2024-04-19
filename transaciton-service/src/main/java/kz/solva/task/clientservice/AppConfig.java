package kz.solva.task.clientservice;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    CqlSession cqlSession = CqlSession.builder().withKeyspace("techtask").build();
//
//    @Bean
//    public CassandraTemplate cassandraTemplate() {
//        return new CassandraTemplate(cqlSession);
//    }
}
