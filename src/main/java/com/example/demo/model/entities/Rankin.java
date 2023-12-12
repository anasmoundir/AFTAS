package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rankins")
public class Rankin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "rankin_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(name = "rank")
    private int rank;
    @Column(name = "score")
    private double score;

}
