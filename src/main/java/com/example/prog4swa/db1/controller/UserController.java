package com.example.prog4swa.db1.controller;

import com.example.prog4swa.db1.model.Company;
import com.example.prog4swa.db1.model.UserEntity;
import com.example.prog4swa.db1.model.UserSession;
import com.example.prog4swa.db1.repository.UserSessionRepository;
import com.example.prog4swa.db1.service.AuthenticationService;
import com.example.prog4swa.db1.service.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@AllArgsConstructor
public class UserController implements WebMvcConfigurer {
    private final AuthenticationService authenticationService;
    @Autowired
    private final UserSessionRepository userSessionRepository;
    private final CompanyService companyService;

    @GetMapping("/login")
    public String showLoginForm(UserEntity userEntity,
                                Model model) {
        Company company = companyService.getCompany();
        model.addAttribute("company", company);
        return "login";
    }

    @PostMapping("/login")
    public String authenticateUser(UserEntity userEntity,
                                   HttpServletResponse response){
        if(authenticationService.authenticate(userEntity.getUserName(), userEntity.getPassword(), response)){
            return "redirect:/employees";
        }
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        String sessionId = authenticationService.extractSessionIdFromRequest(request);
        UserSession userSession = userSessionRepository.findBySessionId(sessionId);
        authenticationService.deleteCookie(response, "userSessionId");
        authenticationService.deleteUserSession(userSession);
        return "redirect:/login";
    }

}
