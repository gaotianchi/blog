package com.gaotianchi.oauth2service.web.error;

public class EntityNotFoundException extends ApplicationException {

    public EntityNotFoundException(final String entity) {
        super(entity + " is not found.");
    }
}
