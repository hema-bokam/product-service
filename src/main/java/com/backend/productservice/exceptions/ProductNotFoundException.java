package com.backend.productservice.exceptions;

public class ProductNotFoundException extends Exception{
    private String message;
    public ProductNotFoundException(String message){
        super(message);
    }
}
