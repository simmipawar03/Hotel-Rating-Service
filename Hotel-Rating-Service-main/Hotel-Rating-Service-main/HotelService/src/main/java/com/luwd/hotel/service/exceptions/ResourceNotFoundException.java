package com.luwd.hotel.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s) {
        super(s);
    }
    public ResourceNotFoundException(){
        super("Resource Not found Exception");
    }
}
