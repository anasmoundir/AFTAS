package com.example.demo.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
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

    @NotBlank(message = "Code cannot be blank")
    @Column(name = "code")
    private String code;

    @NotNull(message = "The date cannot be null")
    @Column(name = "the_date")
    private LocalDate theDate;

    @NotNull(message = "Start time cannot be null")
    @Column(name = "start_time")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    @Column(name = "end_time")
    private LocalTime endTime;

    @PositiveOrZero(message = "Number of participants must be positive or zero")
    @Column(name = "number_of_participant")
    private int numberOfParticipant;

    @NotBlank(message = "Location cannot be blank")
    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.REMOVE)
    private List<Hunting> huntingList;

    @PositiveOrZero(message = "Amount must be positive or zero")
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