package com.mic.dto;

import org.springframework.http.HttpStatus;

public class ErrorProductResponse {

    private String message;
    private HttpStatus httpStatus;

    public ErrorProductResponse() {
    }

    public ErrorProductResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ErrorProductResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
