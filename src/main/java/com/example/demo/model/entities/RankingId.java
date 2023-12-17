package com.example.demo.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RankingId implements Serializable {
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "competition_id")
    private Long competitionId;
}
