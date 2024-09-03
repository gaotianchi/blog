package com.gaotianchi.resourceservice.error;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(final String entity) {
        super(entity + " is not found.");
    }
}
