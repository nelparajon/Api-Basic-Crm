package com.mic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotFoundProductException extends RuntimeException{
    public NotFoundProductException(String message){
        super(message);
    }
}
