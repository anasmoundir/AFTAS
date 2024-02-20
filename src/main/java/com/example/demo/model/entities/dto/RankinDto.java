package com.example.demo.model.entities.dto;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.RankingId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Setter @Getter
@NoArgsConstructor
public class RankinDto {
    private RankingId id;
    private int rank;
    private double score;
}
