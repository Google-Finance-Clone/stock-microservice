package com.stocksmicroservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);  // Set the core pool size
        executor.setMaxPoolSize(50);   // Set the maximum pool size
        executor.setQueueCapacity(100);  // Set the queue capacity``
        executor.setThreadNamePrefix("MyThread-");  // Set the thread name prefix
        executor.initialize();
        return executor;
    }
}
