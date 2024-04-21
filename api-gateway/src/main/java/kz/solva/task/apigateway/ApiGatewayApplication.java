package kz.solva.task.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	// todo ### Я код еще не смотрел. Пока говорю, что вижу так. А вижу я многомодульное приложение,
	//  но если правильно понял, то надо было разбить на микросервисы. То есть отдельные независимые друг от друга
	//  приложения и запускать их соответственно тоже отдельно. Возможно ты просто решил, чтобы не делать пару
	//  репозиториев, все закинуть в один.
	//  C некоторыми технологиями я не сталкивался, поэтому их пропущу. Например, из того что вижу - gradle  и eureka.

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
