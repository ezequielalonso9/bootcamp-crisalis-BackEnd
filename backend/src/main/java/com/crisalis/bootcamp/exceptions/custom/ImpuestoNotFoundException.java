package com.crisalis.bootcamp.exceptions.custom;

public class ImpuestoNotFoundException extends RuntimeException{

    public ImpuestoNotFoundException(String message) {
        super(message);
    }
}
