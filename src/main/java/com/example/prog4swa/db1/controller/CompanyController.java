package com.example.prog4swa.db1.controller;

import com.example.prog4swa.db1.controller.mapper.CompanyMapper;
import com.example.prog4swa.db1.controller.model.EditCompanyModel;
import com.example.prog4swa.db1.model.Company;
import com.example.prog4swa.db1.service.CompanyService;
import com.example.prog4swa.db1.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@AllArgsConstructor
public class CompanyController implements WebMvcConfigurer {
    private final CompanyService service;
    private final EmployeeService employeeService;
    private final CompanyMapper mapper;
    public EditCompanyModel sharedCompany() {
        return mapper.toEditCompanyModel(service.getCompany());
    }

    @GetMapping("/company/edit")
    private ModelAndView showEditCompany(){
        Company actualCompany = service.getCompany();
        return new ModelAndView("edit-company")
                .addObject("editCompanyModel", mapper.toEditCompanyModel(actualCompany));
    }

    @PutMapping(value = "/company/edit", consumes = "multipart/form-data")
    private String editCompany(@Valid EditCompanyModel editCompanyModel,
                               BindingResult result,
                               @RequestParam("logoFile") MultipartFile logoFile){

        if(result.hasErrors()){
            return "edit-company";
        }

        if(logoFile != null && !logoFile.isEmpty()){
            editCompanyModel.setLogo(employeeService.convertToBase64Photo(logoFile));
        }
        service.updateCompany(mapper.toEntity(editCompanyModel));
        return "redirect:/employees";
    }
}
