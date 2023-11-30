package com.spring.security.jwt.exception;

import lombok.Builder;

@Builder
public class BadApiRequestException extends RuntimeException {
    public BadApiRequestException(){
        super("Bad Request !! ");
    }
    public  BadApiRequestException(String message){
        super(message);
    }

}
