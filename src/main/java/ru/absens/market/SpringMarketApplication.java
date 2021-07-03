package ru.absens.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:app.properties")
public class SpringMarketApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringMarketApplication.class, args);
	}
}
