package com.example.prog4swa.db1.controller.mapper;

import com.example.prog4swa.db1.controller.model.AddEmployeeModel;
import com.example.prog4swa.db1.controller.model.EditEmployeeModel;
import com.example.prog4swa.db1.model.Employee;
import com.example.prog4swa.db1.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class EmployeeMapper {
    public final EmployeeService service;

    public Employee toEntity(AddEmployeeModel addEmployee) {
        return Employee.builder()
                .serialNumber(service.generateSerialNumber(addEmployee.getHireDate()))
                .firstName(addEmployee.getFirstName().trim())
                .lastName(addEmployee.getLastName().trim())
                .birthdate(addEmployee.getBirthdate())
                .gender(addEmployee.getGender())
                .phoneNumbers(service.formatStringToPhoneNumbers(addEmployee.getPhoneNumbers()))
                .address(addEmployee.getAddress().trim())
                .emails(List.of(addEmployee.getPersonalEmail().trim(), addEmployee.getProfessionalEmail().trim()))
                .cinNumber(addEmployee.getCinNumber())
                .cinIssuanceDate(addEmployee.getCinIssuanceDate())
                .cinIssuancePlace(addEmployee.getCinIssuancePlace().trim())
                .position(addEmployee.getPosition().trim())
                .dependentChildren(addEmployee.getDependentChildren())
                .hireDate(addEmployee.getHireDate())
                .departureDate(addEmployee.getDepartureDate())
                .salary(addEmployee.getSalary())
                .benefits(addEmployee.getBenefits())
                .allowances(addEmployee.getAllowances())
                .socialSecurityContributions(addEmployee.getSocialSecurityContributions())
                .taxableIncome(addEmployee.getTaxableIncome())
                .taxRate(addEmployee.getTaxRate())
                .cnaps(addEmployee.getCnaps())
                .photo(addEmployee.getPhoto() != null ? service.convertToBase64Photo(addEmployee.getPhoto()) : null)
                .build();
    }

    public Employee toEntity(EditEmployeeModel editEmployee) {
        return Employee.builder()
                .id(editEmployee.getId())
                .serialNumber(editEmployee.getSerialNumber())
                .firstName(editEmployee.getFirstName().trim())
                .lastName(editEmployee.getLastName().trim())
                .birthdate(editEmployee.getBirthdate())
                .gender(editEmployee.getGender())
                .phoneNumbers(service.formatStringToPhoneNumbers(editEmployee.getPhoneNumbers()))
                .address(editEmployee.getAddress().trim())
                .emails(List.of(editEmployee.getPersonalEmail().trim(), editEmployee.getProfessionalEmail().trim()))
                .cinNumber(editEmployee.getCinNumber())
                .cinIssuanceDate(editEmployee.getCinIssuanceDate())
                .cinIssuancePlace(editEmployee.getCinIssuancePlace().trim())
                .position(editEmployee.getPosition().trim())
                .dependentChildren(editEmployee.getDependentChildren())
                .hireDate(editEmployee.getHireDate())
                .departureDate(editEmployee.getDepartureDate())
                .salary(editEmployee.getSalary())
                .benefits(editEmployee.getBenefits())
                .allowances(editEmployee.getAllowances())
                .socialSecurityContributions(editEmployee.getSocialSecurityContributions())
                .taxableIncome(editEmployee.getTaxableIncome())
                .taxRate(editEmployee.getTaxRate())
                .netIncome(editEmployee.getNetIncome())
                .cnaps(editEmployee.getCnaps())
                .photo(editEmployee.getPhoto())
                .build();
    }

    public EditEmployeeModel toEditEmployeeModel(Employee employee) {
        return EditEmployeeModel.builder()
                .id(employee.getId())
                .serialNumber(employee.getSerialNumber())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthdate(employee.getBirthdate())
                .gender(employee.getGender())
                .phoneNumbers(service.formatPhoneNumbersToString(employee.getPhoneNumbers()))
                .address(employee.getAddress())
                .personalEmail(employee.getEmails().get(0))
                .professionalEmail(employee.getEmails().get(1))
                .cinNumber(employee.getCinNumber())
                .cinIssuanceDate(employee.getCinIssuanceDate())
                .cinIssuancePlace(employee.getCinIssuancePlace())
                .position(employee.getPosition().trim())
                .dependentChildren(employee.getDependentChildren())
                .hireDate(employee.getHireDate())
                .departureDate(employee.getDepartureDate())
                .salary(employee.getSalary())
                .benefits(employee.getBenefits())
                .allowances(employee.getAllowances())
                .socialSecurityContributions(employee.getSocialSecurityContributions())
                .taxableIncome(employee.getTaxableIncome())
                .taxRate(employee.getTaxRate())
                .netIncome(employee.getNetIncome())
                .cnaps(employee.getCnaps())
                .photo(employee.getPhoto())
                .build();
    }

}
