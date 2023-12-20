package com.example.demo.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Positive(message = "Num must be a positive integer")
    @Column(name = "num")
    private int num;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name cannot be longer than 255 characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Family Name cannot be blank")
    @Size(max = 255, message = "Family Name cannot be longer than 255 characters")
    @Column(name = "familyName")
    private String familyname;

    @PastOrPresent(message = "Accession Date must be in the past or present")
    @Column(name = "accessionDate")
    private LocalDate accessionDate;

    @NotBlank(message = "Nationality cannot be blank")
    @Size(max = 255, message = "Nationality cannot be longer than 255 characters")
    @Column(name = "nationality")
    private String nationality;

    @NotNull(message = "Identity Document Type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "identityDocumentType")
    private identityDocumentType identitydocumenttype;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rankin> rankings;

    public Set<Rankin> getRankings() {
        return rankings;
    }
}