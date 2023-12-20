package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rankins")
public class Rankin {
    @EmbeddedId
    private RankingId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "competition_id", insertable = false, updatable = false)
    private Competition competition;

    @PositiveOrZero(message = "Rank must be a positive or zero integer")
    @Column(name = "rank")
    private int rank;

    @DecimalMin(value = "0.0", message = "Score must be a positive number or zero")
    @Column(name = "score")
    private double score;

    public Rankin(Member member, Competition competition) {
        this.member = member;
        this.competition = competition;
    }

    public Competition getCompetition() {
        return competition;
    }
}