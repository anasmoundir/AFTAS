package com.example.demo.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class RankingId implements Serializable {
    @NotNull(message = "Member ID is required")
    @Column(name = "member_id")
    private Long memberId;

    @NotNull(message = "Competition ID is required")
    @Column(name = "competition_id")
    private Long competitionId;


}
