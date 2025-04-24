package com.mic.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Usuario con ID " + id + " no encontrado.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

