package com.example.prog4swa.db2.service;

import com.example.prog4swa.db2.model.DB2Employee;
import com.example.prog4swa.db2.repository.DB2EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Db2EmployeService {
    @Autowired
    private final DB2EmployeeRepository employeeRepository;

    public Db2EmployeService(DB2EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public DB2Employee getEmployeeByCnaps(String cnaps) {
        return employeeRepository.findByCnaps(cnaps);
    }

    public void addOrUpdateDb2Employee(DB2Employee db2Employee){
        employeeRepository.save(db2Employee);
    }
}
