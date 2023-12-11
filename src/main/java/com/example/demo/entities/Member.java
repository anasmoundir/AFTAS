package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
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
    @Column(name = "IdentityDocumentType")
    private identityDocumentType identitydocumenttype;
    @ElementCollection
    @CollectionTable(
            name = "competition_emmbers",
            joinColumns = @JoinColumn(name = "member_id")
    )
    @MapKeyJoinColumn(name = "competition_id")
    @AttributeOverrides({
            @AttributeOverride(name = "rank", column = @Column(name = "rank")),
            @AttributeOverride(name = "score", column = @Column(name = "score"))
    })
    private Map<Competition, CompetitionAttributes> competitionsAttributes;
}
