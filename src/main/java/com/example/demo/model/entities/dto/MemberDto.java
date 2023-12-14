package com.example.demo.model.entities.dto;

import com.example.demo.model.entities.identityDocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private int num;
    private String name;
    private String familyname;
    private LocalDate accessionDate;
    private  String nationality;
    private identityDocumentType identitydocumenttype;
}
