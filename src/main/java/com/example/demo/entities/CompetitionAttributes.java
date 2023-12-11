package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionAttributes {
    @Column(name = "rank")
    private int rank;
    @Column(name = "score")
    private double score;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
