package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "competitions")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competition_id", nullable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "the_date")
    private LocalDate theDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "number_of_participant")
    private int numberOfParticipant;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.REMOVE)
    private List<Hunting> huntingList;

    @Column(name = "amount")
    private float amount;
    @OneToMany(mappedBy = "competition")
    private Set<Rankin> rankings;
    public Set<Rankin> getRankings() {
        return rankings;
    }
    public List<Hunting> getHuntings() {
        return huntingList;
    }
}