package com.example.prog4swa.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {
    boolean required() default true;
    String message() default "Telephones invalide, Format: Code pays + 10 chiffres (espace authorise)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

