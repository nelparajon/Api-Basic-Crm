package com.mic.exception;

public class InvalidProductDataException extends RuntimeException{
    public InvalidProductDataException(String message){
        super(message);
    }
}
