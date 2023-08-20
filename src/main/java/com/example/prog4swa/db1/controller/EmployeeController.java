package com.example.prog4swa.db1.controller;

import com.example.prog4swa.db1.controller.mapper.EmployeeMapper;
import com.example.prog4swa.db1.controller.model.AddEmployeeModel;
import com.example.prog4swa.db1.controller.model.EditEmployeeModel;
import com.example.prog4swa.db1.model.Company;
import com.example.prog4swa.db1.model.Employee;
import com.example.prog4swa.db1.service.CompanyService;
import com.example.prog4swa.db1.service.EmployeeService;
import com.example.prog4swa.facade.EmployeeFacade;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class EmployeeController implements WebMvcConfigurer {
    private final EmployeeService service;
    private final CompanyService companyService;
    private final EmployeeMapper mapper;

    private final EmployeeFacade facade;

    @Autowired
    public EmployeeController(EmployeeFacade facade, EmployeeService service,CompanyService companyService, EmployeeMapper mapper) {
        this.facade = facade;
        this.service = service;
        this.companyService = companyService;
        this.mapper = mapper;
    }

    @ModelAttribute("company")
    public Company sharedCompany() {
        return companyService.getCompany();
    }

    @GetMapping("/employees")
    public String showEmployeeList(@RequestParam(name = "firstName", required = false) String firstName,
                                   @RequestParam(name = "lastName", required = false) String lastName,
                                   @RequestParam(name = "gender", required = false) String gender,
                                   @RequestParam(name = "position", required = false) String position,
                                   @RequestParam(name = "hireDate1", required = false) String hireDate1,
                                   @RequestParam(name = "hireDate2", required = false) String hireDate2,
                                   @RequestParam(name = "departureDate1", required = false) String departureDate1,
                                   @RequestParam(name = "departureDate2", required = false) String departureDate2,
                                   @RequestParam(name = "countryCode", required = false) String countryCode,
                                   @RequestParam(name = "sort",  defaultValue = "id,asc") String sort,
                                   @RequestParam(name = "page", defaultValue = "1") int page,
                                   Model model) {

        int pageSize = 10;
        List<Employee> employees = service.customSearch(firstName, lastName, gender, position, hireDate1, hireDate2, departureDate1, departureDate2, countryCode, sort);

        int totalPages = (int) Math.ceil((double) employees.size() / pageSize);

        List<Employee> employeesForPage = employees.stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        model.addAttribute("employees", employeesForPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "employees";
    }


    @GetMapping("/employees/{id}")
    public ModelAndView getEmployeeSheet(@PathVariable int id) {
        Employee employee = service.getEmployeeWithSource(id);
        return new ModelAndView("employee-sheet")
                .addObject("employeeSheet", employee);
    }

    @GetMapping("/employees/add")
    public String showAddEmployeeForm(AddEmployeeModel addEmployeeModel) {
        return "add-employee";
    }

    @PostMapping(value = "/employees/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addEmployee(@Valid AddEmployeeModel addEmployeeModel,
                              BindingResult result) {
        if(result.hasErrors()){
            return "add-employee";
        }
        service.addOrUpdateEmployee(mapper.toEntity(addEmployeeModel));
        return "redirect:/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public ModelAndView showEditEmployeeForm(@PathVariable int id) {
        Employee existingEmployee = service.getEmployeeById(id);
        return new ModelAndView("edit-employee")
                .addObject("editEmployeeModel", mapper.toEditEmployeeModel(existingEmployee));
    }

    @PutMapping(value = "/employees/edit/{id}", consumes = "multipart/form-data")
    public String editEmployee(@PathVariable int id,
                               @Valid EditEmployeeModel editEmployeeModel,
                               BindingResult result,
                               @RequestParam("photoFile") MultipartFile photoFile) {

        if(result.hasErrors()){
            return "edit-employee";
        }

        Employee editEmployee = mapper.toEntity(editEmployeeModel);

        if(photoFile != null && !photoFile.isEmpty()){
            editEmployee.setPhoto(service.convertToBase64Photo(photoFile));
        }

        service.addOrUpdateEmployee(editEmployee);
        return "redirect:/employees/"+id;
    }

    @GetMapping("/employees/payslip/{id}")
    public ModelAndView getEmployeePayslip(@PathVariable int id) {
        Employee employee = service.getEmployeeById(id);
        return new ModelAndView("payslip")
                .addObject("payslip", employee);
    }

    @GetMapping("/employees/payslip/edit/{id}")
    public ModelAndView showEditEmployeePayslipForm(@PathVariable int id) {
        Employee employee = service.getEmployeeById(id);
        return new ModelAndView("edit-employee-payslip")
                .addObject("editEmployeeModel", mapper.toEditEmployeeModel(employee));
    }

    @PutMapping(value = "/employees/payslip/edit/{id}", consumes = "multipart/form-data")
    public String editEmployeePayslip(@PathVariable int id,
                                      @Valid EditEmployeeModel editEmployeeModel,
                                      BindingResult result) {
        if(result.hasErrors()){
            return "edit-employee-payslip";
        }

        Employee editEmployeePayslip = mapper.toEntity(editEmployeeModel);

        service.addOrUpdateEmployee(editEmployeePayslip);
        return "redirect:/employees/payslip/"+id;
    }

    @GetMapping("/export/csv")
    public StreamingResponseBody exportEmployeesAsCSV(HttpServletResponse response,
                                                      @RequestParam(name = "firstName", required = false) String firstName,
                                                      @RequestParam(name = "lastName", required = false) String lastName,
                                                      @RequestParam(name = "gender", required = false) String gender,
                                                      @RequestParam(name = "position", required = false) String position,
                                                      @RequestParam(name = "hireDate1", required = false) String hireDate1,
                                                      @RequestParam(name = "hireDate2", required = false) String hireDate2,
                                                      @RequestParam(name = "departureDate1", required = false) String departureDate1,
                                                      @RequestParam(name = "departureDate2", required = false) String departureDate2,
                                                      @RequestParam(name = "countryCode", required = false) String countryCode,
                                                      @RequestParam(name = "sort", defaultValue = "id,asc") String sort) {
        List<Employee> employees = service.customSearch(firstName, lastName, gender, position, hireDate1, hireDate2, departureDate1, departureDate2, countryCode, sort);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"employees.csv\"");

        return outputStream -> {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            writer.write("Id,Nom,Prenom,Genre,Fonction,DateEmbauche,DateDepart,Telephone\n");

            for (Employee employee : employees) {
                writer.write(
                        employee.getId() + "," +
                                "\"" + employee.getLastName() + "\"," +
                                "\"" + employee.getFirstName() + "\"," +
                                employee.getGender() + "," +
                                "\"" + employee.getPosition() + "\"," +
                                employee.getHireDate() + "," +
                                (employee.getDepartureDate() != null ? employee.getDepartureDate() : "") + "," +
                                employee.getPhoneNumbers() + "\n"
                );
            }

            writer.flush();
        };
    }

}
