package com.example.prog4swa.db2.repository;

import com.example.prog4swa.db2.model.DB2Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DB2EmployeeRepository extends JpaRepository<DB2Employee, Integer> {
    DB2Employee findByCnaps(String cnaps);
}
