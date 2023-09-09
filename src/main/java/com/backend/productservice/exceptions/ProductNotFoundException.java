package com.backend.productservice.exceptions;

public class ProductNotFoundException extends RuntimeException{
    private String message;
    public ProductNotFoundException(String message){
        super(message);
    }
}
