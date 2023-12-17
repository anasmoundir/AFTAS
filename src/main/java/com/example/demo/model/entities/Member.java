package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;
    @Column(name= "num")
    private int num;
    @Column(name = "name")
    private String name;
    @Column(name = "familyName")
    private String familyname;
    @Column(name = "accessionDate")
    private LocalDate accessionDate;
    @Column(name = "nationality")
    private  String nationality;
    @Enumerated(EnumType.STRING)
    @Column(name = "identityDocumentType")
    private identityDocumentType identitydocumenttype;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rankin> rankings;
    public Set<Rankin> getRankings() {
        return rankings;
    }

}
