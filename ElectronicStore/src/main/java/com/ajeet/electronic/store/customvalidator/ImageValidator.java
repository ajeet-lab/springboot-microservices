package com.ajeet.electronic.store.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageValidator implements ConstraintValidator<ImageValid, String> {

    @Override
    public boolean isValid(String message, ConstraintValidatorContext constraintValidatorContext) {
        // Logic
        return !message.isBlank();
    }
}
