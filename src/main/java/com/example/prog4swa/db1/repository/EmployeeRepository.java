package com.example.prog4swa.db1.repository;

import com.example.prog4swa.db1.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findBySerialNumber(String serialNumber);
    Optional<Employee> findByCinNumber(String cinNumber);
    Optional<Employee> findByCnaps(String cnaps);
}
