package com.example.prog4swa.facade;

import com.example.prog4swa.db1.model.Employee;
import com.example.prog4swa.db1.service.EmployeeService;
import com.example.prog4swa.db2.model.DB2Employee;
import com.example.prog4swa.db2.service.Db2EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmployeeFacade{

    private final Db2EmployeService db2EmployeService;

    @Autowired
    public EmployeeFacade(Db2EmployeService db2EmployeService) {

        this.db2EmployeService = db2EmployeService;
    }
    public DB2Employee getEmployeeByCnapsIdFromDb2(String cnaps) {
        return db2EmployeService.getEmployeeByCnaps(cnaps);
    }

}
