package com.mic.exception;

public class OpportunityNotFoundException extends RuntimeException {
    public OpportunityNotFoundException(String publicId) {
        super("No se encontró la oportunidad con publicId: " + publicId);
    }
}
