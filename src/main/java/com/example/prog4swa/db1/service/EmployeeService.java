package com.example.prog4swa.db1.service;

import com.example.prog4swa.db1.model.Employee;
import com.example.prog4swa.db1.repository.EmployeeRepository;
import com.example.prog4swa.db2.model.DB2Employee;
import com.example.prog4swa.db2.service.Db2EmployeService;
import com.example.prog4swa.facade.EmployeeFacade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private final EmployeeRepository repository;
    @Autowired
    private final EntityManager db1EntityManager;

    @Autowired
    private final EmployeeFacade facade;
    private final Db2EmployeService db2EmployeService;

    public EmployeeService(EmployeeRepository repository,
                           @Qualifier("db1EntityManagerFactory") EntityManager db1EntityManager, EmployeeFacade facade, Db2EmployeService db2EmployeService) {
        this.repository = repository;
        this.db1EntityManager = db1EntityManager;
        this.facade = facade;
        this.db2EmployeService = db2EmployeService;
    }

    public List<Employee> getEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(int id) {
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent()){
            return employee.get();
        } else {
            throw new RuntimeException("Resource Employee Not Found");
        }
    }

   public Employee getEmployeeWithSource(int id){
        Employee employee = repository.findById(id).orElse(null);

        if(employee != null){
            DB2Employee db2Employee = facade.getEmployeeByCnapsIdFromDb2(employee.getCnaps());

            if(db2Employee != null) {
                employee.setCnaps(db2Employee.getCnaps());
            }
        }
        return employee;
    }
    public void addOrUpdateEmployee(Employee newEmployee){
        repository.save(newEmployee);

        DB2Employee db2Employee = new DB2Employee();
        db2Employee.setId(newEmployee.getId());
        db2Employee.setCnaps(newEmployee.getCnaps());

        db2EmployeService.addOrUpdateDb2Employee(db2Employee);
    }

    public boolean isSerialNumberExists(String serialNumber) {
        Optional<Employee> optionalEmployee = repository.findBySerialNumber(serialNumber);
        return optionalEmployee.isPresent();
    }

    public List<Employee> customSearch(String firstName, String lastName, String gender, String position, String hireDate1, String hireDate2, String departureDate1, String departureDate2, String countryCode, String sort) {
        LocalDate date = LocalDate.now();
        StringBuilder queryString = new StringBuilder("SELECT e FROM Employee e WHERE 1 = 1");
        if (firstName != null && !firstName.isEmpty()) {
            queryString.append(" AND e.firstName ILIKE :firstName");
        }
        if (lastName != null && !lastName.isEmpty()) {
            queryString.append(" AND e.lastName ILIKE :lastName");
        }
        if (gender != null && !gender.isEmpty()) {
            queryString.append(" AND e.gender = :gender");
        }
        if (position != null && !position.isEmpty()) {
            queryString.append(" AND e.position ILIKE :position");
        }
        if (hireDate1 != null && !hireDate1.isEmpty()) {
            queryString.append(" AND e.hireDate >= :hireDate1");
            if (hireDate2 != null && !hireDate2.isEmpty()) {
                queryString.append(" AND e.hireDate <= :hireDate2");
            } else {
                queryString.append(" AND e.hireDate <= :date");
            }
        }
        if (departureDate1 != null && !departureDate1.isEmpty()) {
            queryString.append(" AND e.departureDate >= :departureDate1");
            if (departureDate2 != null && !departureDate2.isEmpty()) {
                queryString.append(" AND e.departureDate <= :departureDate2");
            } else {
                queryString.append(" AND e.departureDate <= :date");
            }
        }
        if (countryCode != null && !countryCode.isEmpty()) {
            queryString.append(" AND EXISTS (SELECT p FROM e.phoneNumbers p WHERE p ILIKE :countryCode)");
        }

        if (!sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            String sortField = sortParams[0];
            String sortOrder = sortParams[1];
            queryString.append(" ORDER BY e.").append(sortField).append(" ").append(sortOrder);
        }

        Query query = db1EntityManager.createQuery(queryString.toString());

        if (firstName != null && !firstName.isEmpty()) {
            query.setParameter("firstName", "%" + firstName.trim() + "%");
        }
        if (lastName != null && !lastName.isEmpty()) {
            query.setParameter("lastName", "%" + lastName.trim() + "%");
        }
        if (gender != null && !gender.isEmpty()) {
            query.setParameter("gender", Employee.Gender.valueOf(gender));
        }
        if (position != null && !position.isEmpty()) {
            query.setParameter("position", "%" + position.trim() + "%");
        }
        if (hireDate1 != null && !hireDate1.isEmpty()) {
            query.setParameter("hireDate1", LocalDate.parse(hireDate1));
            if (hireDate2 != null && !hireDate2.isEmpty()) {
                query.setParameter("hireDate2", LocalDate.parse(hireDate2));
            } else {
                query.setParameter("date", date);
            }
        }
        if (departureDate1 != null && !departureDate1.isEmpty()) {
            query.setParameter("departureDate1", LocalDate.parse(departureDate1));
            if (departureDate2 != null && !departureDate2.isEmpty()) {
                query.setParameter("departureDate2", LocalDate.parse(departureDate2));
            } else {
                query.setParameter("date", date);
            }
        }
        if (countryCode != null && !countryCode.isEmpty()) {
            query.setParameter("countryCode", "%" + countryCode.trim() + "%");
        }

        return query.getResultList();
    }

    public String convertToBase64Photo(MultipartFile photoFile) {
        try {
            byte[] photoBytes = photoFile.getBytes();
            return Base64.getEncoder().encodeToString(photoBytes);
        } catch (IOException e) {
            return null;
        }
    }

    public String generateSerialNumber(LocalDate hireDate) {
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000; //10000 - 99999
        String serialNumber = "EMP-" + hireDate.toString() + "-" + randomNumber;

        while (isSerialNumberExists(serialNumber)) {
            randomNumber = random.nextInt(90000) + 10000;
            serialNumber = "EMP-" + hireDate.toString() + "-" + randomNumber;
        }

        return serialNumber;
    }

    public String formatPhoneNumbersToString(List<String> phoneNumbers) {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            return null;
        }
        StringJoiner joiner = new StringJoiner("\n");

        for (String phoneNumber : phoneNumbers) {
            joiner.add(phoneNumber.trim());
        }
        return joiner.toString();
    }

    public List<String> formatStringToPhoneNumbers(String phoneNumbers) {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            return new ArrayList<>();
        }
        String[] phoneNumbersArray = phoneNumbers.split("\n");
        List<String> formattedPhoneNumbers = new ArrayList<>();
        for (String phoneNumber : phoneNumbersArray) {
            formattedPhoneNumbers.add(phoneNumber.trim());
        }
        return formattedPhoneNumbers;    }
}
