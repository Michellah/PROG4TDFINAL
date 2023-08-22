package com.example.prog4swa.validation;

import com.example.prog4swa.db1.model.Employee;
import com.example.prog4swa.db1.service.EmployeeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private boolean REQUIRED;
    private final EmployeeService employeeService;

    public PhoneNumberValidator(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        REQUIRED = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!REQUIRED && value.isEmpty()) {
            return true;
        }
        List<String> phoneNumbersList = employeeService.formatStringToPhoneNumbers(value);
        boolean validPhoneNumberFound = false;
        for (String phone : phoneNumbersList) {
            boolean countryCodeMatched = false;
            for (Employee.CountryCode countryCode : Employee.CountryCode.values()) {
                if (phone.startsWith(countryCode.getCode())) {
                    countryCodeMatched = true;
                    String phoneNumberWithoutCountryCode = phone.substring(countryCode.getCode().length()).replaceAll("\\s", "");
                    if (!phoneNumberWithoutCountryCode.matches("\\d{10}")) {
                        return false;
                    }
                }
            }
            if (!countryCodeMatched) {
                return false;
            }
            validPhoneNumberFound = true;
        }
        return validPhoneNumberFound;
    }

}