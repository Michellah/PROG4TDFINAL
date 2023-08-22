package com.example.prog4swa.db1.controller.security;

import com.example.prog4swa.db1.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class SessionFilter implements HandlerInterceptor {
    private final AuthenticationService authenticationService;

    public SessionFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws IOException {
        String sessionId = authenticationService.extractSessionIdFromRequest(request);
        String requestURI = request.getRequestURI();
        boolean userLoggedIn = authenticationService.isUserLoggedIn(sessionId);

        if (userLoggedIn) {
            if (requestURI.equals("/login")) {
                response.sendRedirect("/employees");
                return false;
            }
            return true;
        } else {
            if (requestURI.equals("/login")) {
                return true;
            } else {
                response.sendRedirect("/login");
                return false;
            }
        }
    }

}
