package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "competition")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;

    @Column(name = "startTime")
    private Date startTime;

    @Column(name = "endTime")
    private Date endTime;

    @Column(name = "numberOfParticipant")
    private int numberOfParticipant;

    @Column(name = "location")
    private String location;

    @Column(name = "amount")
    private float amount;

    @ElementCollection
    @CollectionTable(
            name = "member_competition_rank",
            joinColumns = @JoinColumn(name = "competition_id")
    )
    @MapKeyJoinColumn(name = "member_id")
    private Map<Member, CompetitionAttributes> membersAttributes;

}
