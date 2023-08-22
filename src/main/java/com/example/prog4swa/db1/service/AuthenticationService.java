package com.example.prog4swa.db1.service;

import com.example.prog4swa.db1.model.UserEntity;
import com.example.prog4swa.db1.model.UserSession;
import com.example.prog4swa.db1.repository.UserRepository;
import com.example.prog4swa.db1.repository.UserSessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;

    public boolean authenticate(String userName, String password, HttpServletResponse response) {
        UserEntity userEntity = userRepository.findByUserName(userName);
        if (userEntity != null && userEntity.getPassword().equals(password)) {
            UserSession session = new UserSession();
            session.setUserEntity(userEntity);
            session.setSessionId(UUID.randomUUID().toString());
            session.setExpirationDate(LocalDateTime.now().plusHours(12));
            UserSession userSession = userSessionRepository.save(session);
            Cookie cookie = new Cookie("userSessionId", userSession.getSessionId());
            cookie.setPath("/");
            cookie.setMaxAge(12 * 60 * 60);

            response.addCookie(cookie);

            return true;
        }
        return false;
    }

    public String extractSessionIdFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userSessionId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean isUserLoggedIn(String sessionId){
        if (sessionId != null) {
            UserSession session = userSessionRepository.findBySessionId(sessionId);
            return session != null && session.getExpirationDate().isAfter(LocalDateTime.now());
        }
        return false;
    }

    public void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void deleteUserSession(UserSession userSession){
        userSessionRepository.delete(userSession);
    }
}
