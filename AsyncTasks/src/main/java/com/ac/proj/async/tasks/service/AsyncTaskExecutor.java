package com.ac.proj.async.tasks.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class AsyncTaskExecutor {

    private final ExecutorService mainThreadExecutor;
    private final ExecutorService virtualThreadExecutor;

    public AsyncTaskExecutor(@Qualifier("mainThreadExecutor") ExecutorService mainThreadExecutor,
                             @Qualifier("virtualThreadExecutor") ExecutorService virtualThreadExecutor) {
        this.mainThreadExecutor = mainThreadExecutor;
        this.virtualThreadExecutor = virtualThreadExecutor;
    }

    public <T> CompletableFuture<T> executeAsync(Callable<T> task, boolean useVirtualThreads) {
        ExecutorService executor = useVirtualThreads ? virtualThreadExecutor : mainThreadExecutor;
        return CompletableFuture.supplyAsync(() -> {
            try {
                return task.call();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    public void shutdownExecutors() {
        mainThreadExecutor.shutdown();
        virtualThreadExecutor.shutdown();
    }
}
