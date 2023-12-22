package com.example.demo.model.entities.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    private String code;
    @NotNull(message = "The date cannot be null")
    private LocalDate theDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "End time cannot be null")
    private LocalTime startTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;
    @PositiveOrZero(message = "Number of participants must be positive or zero")
    private int numberOfParticipant;
    @NotBlank(message = "Location cannot be blank")
    private String location;
    @PositiveOrZero(message = "Amount must be positive or zero")
    private float amount;
}
