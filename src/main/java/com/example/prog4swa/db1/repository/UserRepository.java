package com.example.prog4swa.db1.repository;

import com.example.prog4swa.db1.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUserName(String userName);
}
