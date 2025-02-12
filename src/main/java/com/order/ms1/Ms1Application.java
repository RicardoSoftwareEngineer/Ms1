package com.order.ms1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ms1Application {
	public static final String topicExchangeName = "spring-boot-exchange";
	public static final String queueName = "spring-boot";
	public static void main(String[] args) {
		SpringApplication.run(Ms1Application.class, args);
	}
}
