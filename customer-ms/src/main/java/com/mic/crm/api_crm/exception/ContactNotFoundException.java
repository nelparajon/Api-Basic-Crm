package com.mic.crm.api_crm.exception;

import org.springframework.http.HttpStatus;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}

