package com.ajeet.electronic.store.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageValidator implements ConstraintValidator<ImageValid, String> {

    @Override
    public boolean isValid(String message, ConstraintValidatorContext constraintValidatorContext) {
        // Logic
        return !message.isBlank();
    }
}
