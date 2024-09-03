package com.gaotianchi.resourceservice.error;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(){
        super();
    }
    public UserAlreadyExistException(final String message){
        super(message);
    }
}
