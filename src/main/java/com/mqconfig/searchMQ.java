package com.mqconfig;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class searchMQ {
    public static final String QUEUE_NAME = "searchSender";

    @Bean
    public Queue investmentQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}

