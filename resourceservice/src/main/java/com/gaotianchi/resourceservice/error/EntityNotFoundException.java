package com.gaotianchi.resourceservice.error;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(Object object) {
        super(object.getClass().getSimpleName() + " is not found.");
    }
}
