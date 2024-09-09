package com.gaotianchi.auth.web.controller;

import com.gaotianchi.auth.web.error.EntityAlreadyExistException;
import com.gaotianchi.auth.web.error.EntityNotFoundException;
import com.gaotianchi.auth.web.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public <T> APIResponse<T> handleEntityNotFoundException(EntityNotFoundException e) {
        return APIResponse.fail(404, e.getLocalizedMessage());
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public <T> APIResponse<T> handleEntityAlreadyExistException(EntityAlreadyExistException e) {
        return APIResponse.fail(409, e.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> APIResponse<T> handleException(Exception e) {
        System.out.println("unknown exception: " + e.getMessage());
        return APIResponse.fail(400, e.getLocalizedMessage());
    }
}