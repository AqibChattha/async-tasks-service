package com.ac.proj.async.tasks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ExecutorConfig {

    @Value("${async.tasks.main-thread.pool-size}")
    private int mainThreadPoolSize;

    @Value("${async.tasks.virtual-thread.enabled}")
    private boolean virtualThreadEnabled;

    @Bean(name = "mainThreadExecutor")
    public ExecutorService mainThreadExecutor() {
        return Executors.newFixedThreadPool(mainThreadPoolSize);
    }

    @Bean(name = "virtualThreadExecutor")
    public ExecutorService virtualThreadExecutor() {
        if (virtualThreadEnabled) {
            return Executors.newVirtualThreadPerTaskExecutor();
        } else {
            return Executors.newCachedThreadPool();
        }
    }
}
