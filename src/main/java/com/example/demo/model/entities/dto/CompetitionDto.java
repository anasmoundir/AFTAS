package com.example.demo.model.entities.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompetitionDto {
    private Long id;
//    private String code;
//    @JsonDeserialize(using = LocalDateDeserializer.class)

    private LocalDate theDate;

//    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;
//    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;


    private int numberOfParticipant ;
    private String location;
    private float amount;
}
