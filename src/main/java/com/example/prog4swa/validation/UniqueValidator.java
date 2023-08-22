package com.example.prog4swa.validation;

import com.example.prog4swa.db1.repository.EmployeeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class UniqueValidator implements ConstraintValidator<ValidUnique, String> {

    private final EmployeeRepository repository;
    private boolean REQUIRED;

    public UniqueValidator(EmployeeRepository repository) {
        this.repository = repository;
    }

    private String VALUE;

    @Override
    public void initialize(ValidUnique constraintAnnotation) {
        REQUIRED = constraintAnnotation.required();
        VALUE = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String valueUnique, ConstraintValidatorContext context) {
        if(!REQUIRED && valueUnique.isEmpty()){
            return true;
        }
        if(Objects.equals(VALUE, "cinNumber"))
            return repository.findByCinNumber(valueUnique).isEmpty();
        if(Objects.equals(VALUE, "cnaps"))
            return repository.findByCnaps(valueUnique).isEmpty();
        return !valueUnique.isEmpty();
    }
}

