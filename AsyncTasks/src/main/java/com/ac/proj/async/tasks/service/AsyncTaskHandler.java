package com.ac.proj.async.tasks.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class AsyncTaskHandler {

    public boolean cancelTask(Future<?> future) {
        return future.cancel(true);
    }

    public <T> CompletableFuture<T> retryTask(Callable<T> task, ExecutorService executor, int retries) {
        CompletableFuture<T> future = new CompletableFuture<>();
        executeWithRetry(task, executor, retries, future);
        return future;
    }

    private <T> void executeWithRetry(Callable<T> task, ExecutorService executor, int retries, CompletableFuture<T> future) {
        executor.submit(() -> {
            int attempt = 0;
            while (attempt < retries) {
                try {
                    T result = task.call();
                    future.complete(result);
                    return;
                } catch (Exception e) {
                    attempt++;
                    if (attempt >= retries) {
                        future.completeExceptionally(e);
                    }
                }
            }
        });
    }
}
