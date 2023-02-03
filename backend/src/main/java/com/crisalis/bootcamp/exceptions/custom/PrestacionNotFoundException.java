package com.crisalis.bootcamp.exceptions.custom;

public class PrestacionNotFoundException extends RuntimeException {

    public PrestacionNotFoundException(String message){
        super(message);
    }
}
