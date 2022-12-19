package com.crisalis.bootcamp.exceptions.custom;

public class ClienteNotFoundException extends RuntimeException{

    public ClienteNotFoundException(String message){
        super(message);
    }
}
