package com.stocksmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.stocksmicroservice", "com.mqconfig"})
public class StocksMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksMicroserviceApplication.class, args);
	}

}
