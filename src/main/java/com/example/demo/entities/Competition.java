package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
@Data
@Entity
@NoArgsConstructor
@Table(name = "competitions")
public class Competition {
    @Id
    @Column(name = "competition_id", nullable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "the_date")
    private Date theDate;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "number_of_participant")
    private int numberOfParticipant;

    @Column(name = "location")
    private String location;

    @Column(name = "amount")
    private float amount;

}