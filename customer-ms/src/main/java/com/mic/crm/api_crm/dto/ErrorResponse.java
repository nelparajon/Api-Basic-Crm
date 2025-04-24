package com.mic.crm.api_crm.dto;

import org.springframework.http.HttpStatus;


/**
 * Class that provides a message and an HTTP status for error handling.
 */
public class ErrorResponse {

    private String message; // Error message describing the issue
    private HttpStatus status; // HTTP status code associated with the error

    /**
     * Constructor to create an ErrorResponse with a message and an HTTP status.
     *
     * @param message The error message.
     * @param status  The HTTP status code.
     */
    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    /**
     * Constructor to create an ErrorResponse with only a message.
     * @param message The error message.
     */
    public ErrorResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}

