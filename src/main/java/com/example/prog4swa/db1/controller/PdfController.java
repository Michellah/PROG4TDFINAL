package com.example.prog4swa.db1.controller;

import com.example.prog4swa.db1.model.Employee;
import com.example.prog4swa.db1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private EmployeeService employeeService; // Assuming you have a service to fetch employee data

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/employee/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> generateEmployeePdf(@PathVariable int id) throws Exception {
        Employee employee = employeeService.getEmployeeById(id);

        LocalDate birthdate = employee.getBirthdate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthdate, currentDate).getYears();
        Map<String, Object> model = new HashMap<>();
        model.put("employee", employee);
        model.put("age", age);

        String htmlContent = loadHtmlTemplate("employee-sheet.html");

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(pdfOutputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "employee-sheet.pdf");


        return new ResponseEntity<>(pdfOutputStream.toByteArray(), headers, HttpStatus.OK);

    }

    private String loadHtmlTemplate(String templatePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/templates/" + templatePath);
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return new String(bytes);
        }
    }

    private String loadCssStyles(String cssPath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/static/" + cssPath);
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return new String(bytes);
        }
    }

    private String addCssStyles(String htmlContent, String cssContent) {
        return htmlContent.replace("</head>", "<style>" + cssContent + "</style></head>");
    }
}
