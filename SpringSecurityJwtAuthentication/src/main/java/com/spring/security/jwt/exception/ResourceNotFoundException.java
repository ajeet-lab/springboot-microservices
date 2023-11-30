package com.spring.security.jwt.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {

    ResourceNotFoundException(){
        super("Resource not found !!");
    }

    ResourceNotFoundException(String message){
        super(message);
    }
}
