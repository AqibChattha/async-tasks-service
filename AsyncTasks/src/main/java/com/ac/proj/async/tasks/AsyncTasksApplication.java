package com.ac.proj.async.tasks;

import com.ac.proj.async.tasks.service.AsyncTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;

public class AsyncTasksApplication {
    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    public void runAsyncTask() {
        CompletableFuture<String> future = asyncTaskExecutor.executeAsync(() -> {
            // Simulate long-running task
            Thread.sleep(1000);
            return "Task Completed!";
        }, false);

        future.thenAccept(result -> System.out.println("Result: " + result))
                .exceptionally(e -> {
                    System.err.println("Task failed: " + e.getMessage());
                    return null;
                });
    }

    public static void main(String[] args) {
        AsyncTasksApplication app = new AsyncTasksApplication();
        app.runAsyncTask();
    }
}
