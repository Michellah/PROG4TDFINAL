package com.example.prog4swa.db1.repository;

import com.example.prog4swa.db1.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository  extends JpaRepository<UserSession, Integer> {
    UserSession findBySessionId(String sessionId);
}
