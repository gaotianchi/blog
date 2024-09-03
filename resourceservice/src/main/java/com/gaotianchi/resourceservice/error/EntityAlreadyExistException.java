package com.gaotianchi.resourceservice.error;

public class EntityAlreadyExistException extends Exception {
    public EntityAlreadyExistException(final String entity) {
        super(entity + " already exist.");
    }
}
