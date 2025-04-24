package com.mic.exception;

public class UserEmailNotFoundException extends RuntimeException {

    public UserEmailNotFoundException(String email) {
        super("Usuario con email " + email + " no encontrado.");
    }


}
