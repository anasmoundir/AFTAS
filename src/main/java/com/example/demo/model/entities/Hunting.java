package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hunting")
public class Hunting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nombreOffish")
    private int nombreOffish;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "fish_id")
    private Fish fish;

    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    public Hunting(Member member, Fish fish, int points) {
        this.member =member;
        this.fish =fish;

    }

    public int getPoints() {
        if (this.fish != null) {
            if (this.fish.getLevel() != null) {
                int baseScore = this.fish.getLevel().getPoints();
                int nombreOffish = this.nombreOffish;
                double logMultiplier = Math.log1p(nombreOffish);
                return (int) (baseScore * logMultiplier);
            }
        }
        return 0;
    }
}
