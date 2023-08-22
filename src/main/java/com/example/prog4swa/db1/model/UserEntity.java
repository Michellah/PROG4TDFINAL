package com.example.prog4swa.db1.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_name")
    private String userName;
    private String password;

}