package kz.solva.task.transacitonservice;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransacitonServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TransacitonServiceApplication.class, args);
	}

}
