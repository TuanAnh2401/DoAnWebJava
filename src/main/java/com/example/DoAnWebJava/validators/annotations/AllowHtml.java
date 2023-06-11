package com.example.DoAnWebJava.validators.annotations;

import com.example.DoAnWebJava.validators.HtmlValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HtmlValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowHtml {
    String message() default "Invalid HTML content";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
