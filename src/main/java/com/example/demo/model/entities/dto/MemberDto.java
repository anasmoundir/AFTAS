package com.example.demo.model.entities.dto;

import com.example.demo.model.entities.identityDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private int num;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Family Name cannot be blank")
    private String familyname;
    @PastOrPresent(message = "Accession Date must be in the past or present")
    private LocalDate accessionDate;
    @NotBlank(message = "Nationality cannot be blank")
    private  String nationality;
    @NotNull(message = "Identity Document Type cannot be null")
    private identityDocumentType identitydocumenttype;
}
