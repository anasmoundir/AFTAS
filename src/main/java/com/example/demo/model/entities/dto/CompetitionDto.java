package com.example.demo.model.entities.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CompetitionDto {
    private Long id;
    private String code;
    private Date theDate;
    private Date startTime;
    private Date endTime;
    private int numberOfParticipant;
    private String location;
    private float amount;
}
