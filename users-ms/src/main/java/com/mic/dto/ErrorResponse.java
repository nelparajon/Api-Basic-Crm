package com.mic.dto;

import java.time.Instant;
import java.util.List;

public class ErrorResponse {
    private String message;
    private List<String> errors;
    private int status;
    private Instant timestamp = Instant.now();

    public ErrorResponse() {
    }

    public ErrorResponse(String message, List<String> errors, int status) {
        this.message = message;
        this.errors = errors;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}

