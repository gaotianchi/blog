package com.gaotianchi.resourceservice.error;

public class EntityAlreadyExistException extends ApplicationException {
    public EntityAlreadyExistException(final String entity) {
        super(entity + " already exist.");
    }
}
