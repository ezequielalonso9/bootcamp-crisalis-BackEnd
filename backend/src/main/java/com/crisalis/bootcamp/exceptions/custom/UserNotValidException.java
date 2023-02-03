package com.crisalis.bootcamp.exceptions.custom;

public class UserNotValidException extends RuntimeException {

    public UserNotValidException(String message){
        super(message);
    }
}
