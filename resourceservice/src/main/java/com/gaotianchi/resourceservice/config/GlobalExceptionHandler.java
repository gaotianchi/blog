package com.gaotianchi.resourceservice.config;

import com.gaotianchi.resourceservice.web.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> APIResponse<T> handleException(Exception e) {
        System.out.println("unknown exception: " + e.getMessage());
        return APIResponse.fail(400, e.getLocalizedMessage());
    }
}