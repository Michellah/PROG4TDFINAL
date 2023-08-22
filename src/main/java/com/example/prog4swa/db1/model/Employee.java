package com.example.prog4swa.db1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
@Builder
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, name = "serial_number")
    private String serialNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private LocalDate birthdate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ElementCollection
    @Column(unique = true, name = "phone_numbers")
    @CollectionTable(name = "all_phone_numbers", joinColumns = @JoinColumn(name = "employee_id"))
    private List<String> phoneNumbers;
    private String address;
    @ElementCollection
    @Column(unique = true)
    @CollectionTable(name = "all_emails", joinColumns = @JoinColumn(name = "employee_id"))
    private List<String> emails;
    @Column(unique = true, name = "cin_number")
    private String cinNumber;
    @Column(name = "cin_issuance_date")
    private LocalDate cinIssuanceDate;
    @Column(name = "cin_issuance_place")
    private String cinIssuancePlace;
    private String position;
    @Column(name = "dependent_children")
    private int dependentChildren;
    @Column(name = "hire_date")
    private LocalDate hireDate;
    @Column(name = "departure_date")
    private LocalDate departureDate;
    private double salary;
    private double benefits;
    private double allowances;
    @Column(name = "social_security_contributions")
    private double socialSecurityContributions;
    @Column(name = "taxable_income")
    private double taxableIncome;
    @Column(name = "tax_rate")
    private double taxRate;
    @Column(name = "net_income")
    private double netIncome;
    @Column(unique = true)
    private String cnaps;
    @Column(length = 10000000)
    private String photo;

    public enum Gender {
        H,
        F
    };

    public enum CountryCode {
        MADAGASCAR("+261"),
        FRANCE("+33");

        private final String code;

        CountryCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

}
