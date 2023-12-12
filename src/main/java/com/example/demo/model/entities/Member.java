package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id", nullable = false)
    private Long id;
    @Column(name= "num")
    private int num;
    @Column(name = "name")
    private String name;
    @Column(name = "familyName")
    private String familyname;
    @Column(name = "accessionDate")
    private Date accessionDate;
    @Column(name = "nationality")
    private  String nationality;
    @Enumerated(EnumType.STRING)
    @Column(name = "identityDocumentType")
    private identityDocumentType identitydocumenttype;
    @Transient
    private Set<Rankin> rankins = new HashSet<Rankin>();
    @OneToMany(mappedBy = "member")
    public Set<Rankin> getRanking() {
        return rankins;
    }

}