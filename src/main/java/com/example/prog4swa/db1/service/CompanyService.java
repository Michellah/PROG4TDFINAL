package com.example.prog4swa.db1.service;

import com.example.prog4swa.db1.repository.CompanyRepository;
import com.example.prog4swa.db1.model.Company;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {
    @Autowired
    private final CompanyRepository repository;

    public Company getCompany() {
        return repository.findAll().get(0);
    }

    public void updateCompany(Company editCompany){
        repository.save(editCompany);
    }

}
