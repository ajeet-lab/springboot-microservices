package com.ajeet.blog.ajeetblog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String resourceField;
    long fieldValue;


    public ResourceNotFoundException(String resourceName, String resourceField, long fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, resourceField, fieldValue));
        this.resourceName = resourceName;
        this.resourceField = resourceField;
        this.fieldValue = fieldValue;
    } 
}
