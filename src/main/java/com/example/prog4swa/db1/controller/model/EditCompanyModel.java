package com.example.prog4swa.db1.controller.model;

import com.example.prog4swa.validation.ValidPhoneNumber;
import com.example.prog4swa.validation.ValidSimplePattern;
import com.example.prog4swa.validation.ValidSimpleString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class EditCompanyModel {
    private int id;
    @ValidSimpleString
    private String name;
    @ValidSimpleString(max = 255)
    private String description;
    @ValidSimpleString(max = 255)
    private String slogan;
    @ValidSimpleString(max = 255)
    private String address;
    private String email;
    @ValidPhoneNumber
    private String phoneNumbers;
    @ValidSimplePattern(pattern = "\\d{10}", message = "NIF doit etre au format de 10 chiffres")
    private String nif;
    @ValidSimplePattern(pattern = "\\d{10}", message = "STAT doit etre au format de 10 chiffres")
    private String stat;
    @ValidSimplePattern(pattern = "\\d{5}", message = "RCS doit etre au format de 5 chiffres")
    private String rcs;
    private String logo;
}
