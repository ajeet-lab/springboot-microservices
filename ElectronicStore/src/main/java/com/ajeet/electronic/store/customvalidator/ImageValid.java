package com.ajeet.electronic.store.customvalidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
public @interface ImageValid {

    String message() default "Image is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
