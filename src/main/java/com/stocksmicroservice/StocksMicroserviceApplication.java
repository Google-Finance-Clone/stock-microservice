package com.stocksmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.stocksmicroservice", "com.mqconfig"})

public class StocksMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksMicroserviceApplication.class, args);
	}

}
