package com.mic.dto;

import java.time.Instant;

public class ApiResponse<T> {
    private String message;
    private T data;
    private Instant timestamp = Instant.now();

    public ApiResponse() {}

    public ApiResponse( String message, T data) {
        this.message = message;
        this.data = data;
    }

    // Getters y Setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
