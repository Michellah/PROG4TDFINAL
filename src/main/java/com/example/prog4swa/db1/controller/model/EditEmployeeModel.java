package com.example.prog4swa.db1.controller.model;

import com.example.prog4swa.db1.model.Employee;
import com.example.prog4swa.validation.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditEmployeeModel {
    private int id;
    private String serialNumber;
    @ValidSimpleString
    private String firstName;
    @ValidSimpleString
    private String lastName;
    @ValidDate(value = 18, message = "L' employée doit avoir plus de 18 ans et moins de 60 ans")
    private LocalDate birthdate;
    private Employee.Gender gender;
    @ValidPhoneNumber
    private String phoneNumbers;
    @ValidSimpleString
    private String address;
    private String personalEmail;
    private String professionalEmail;
    @ValidSimplePattern(pattern = "\\d{12}", message = "CIN doit etre au format de 12 chiffres")
    private String cinNumber;
    @ValidDate(value = 0)
    private LocalDate cinIssuanceDate;
    @ValidSimpleString
    private String cinIssuancePlace;
    @ValidSimpleString
    private String position;
    @Min(value = 0, message = "La valeur doit être supérieur ou égal à 0.")
    private int dependentChildren;
    @ValidDate(value = 0)
    private LocalDate hireDate;
    @ValidDate(value = 0, required = false)
    private LocalDate departureDate;
    @Min(value = 0, message = "La valeur doit être supérieur ou égal à 0.")
    private double salary;
    @Min(value = 0, message = "La valeur doit être supérieur ou égal à 0.")
    private double benefits;
    @Min(value = 0, message = "La valeur doit être supérieur ou égal à 0.")
    private double allowances;
    @Min(value = 0, message = "La valeur doit être supérieur ou égal à 0.")
    private double socialSecurityContributions;
    @Min(value = 0, message = "La valeur doit être supérieur ou égal à 0.")
    private double taxableIncome;
    @Min(value = 0, message = "La valeur doit être supérieur ou égal à 0.")
    private double taxRate;
    @Min(value = 0, message = "La valeur doit être supérieur ou égal à 0.")
    private double netIncome;
    @ValidSimplePattern(pattern = "^CNAPS-\\d{3}-\\d{5}$", message = "Le numéro CNAPS doit etre au format CNAPS-XXX-XXXXX")
    private String cnaps;
    private String photo;
}

