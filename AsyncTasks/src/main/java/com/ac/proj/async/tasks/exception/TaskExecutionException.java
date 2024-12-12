package com.ac.proj.async.tasks.exception;

public class TaskExecutionException extends RuntimeException {
    public TaskExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
