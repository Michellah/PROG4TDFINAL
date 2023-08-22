package com.example.prog4swa.db1.controller.mapper;

import com.example.prog4swa.db1.controller.model.EditCompanyModel;
import com.example.prog4swa.db1.model.Company;
import com.example.prog4swa.db1.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CompanyMapper {
    public final EmployeeService service;

    public Company toEntity(EditCompanyModel editCompany){
        return Company.builder()
                .id(editCompany.getId())
                .name(editCompany.getName())
                .description(editCompany.getDescription())
                .slogan(editCompany.getSlogan())
                .address(editCompany.getAddress())
                .email(editCompany.getEmail())
                .phoneNumbers(service.formatStringToPhoneNumbers(editCompany.getPhoneNumbers()))
                .nif(editCompany.getNif())
                .stat(editCompany.getStat())
                .rcs(editCompany.getRcs())
                .logo(editCompany.getLogo())
                .build();
    }

    public EditCompanyModel toEditCompanyModel(Company company){
        return EditCompanyModel.builder()
                .id(company.getId())
                .name(company.getName().trim())
                .description(company.getDescription().trim())
                .slogan(company.getSlogan().trim())
                .address(company.getAddress().trim())
                .email(company.getEmail().trim())
                .phoneNumbers(service.formatPhoneNumbersToString(company.getPhoneNumbers()))
                .nif(company.getNif())
                .stat(company.getStat())
                .rcs(company.getRcs())
                .logo(company.getLogo())
                .build();
    }

}
