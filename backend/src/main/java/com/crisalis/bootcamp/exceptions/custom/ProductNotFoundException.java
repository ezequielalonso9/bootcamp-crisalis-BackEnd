package com.crisalis.bootcamp.exceptions.custom;

import java.util.function.Supplier;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message){
        super(message);
    }
}
