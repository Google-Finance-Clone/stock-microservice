package com.stocksmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.stocksmicroservice", "com.mqconfig"})
@EnableCaching
public class StocksMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksMicroserviceApplication.class, args);
	}

}
