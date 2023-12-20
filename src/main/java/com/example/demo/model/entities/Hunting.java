package com.example.demo.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @PositiveOrZero(message = "Number of fish must be a positive or zero")
    @Column(name = "nombreOffish")
    private int nombreOffish;

    @NotNull(message = "Member cannot be null")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull(message = "Fish cannot be null")
    @ManyToOne
    @JoinColumn(name = "fish_id")
    private Fish fish;

    @NotNull(message = "Competition cannot be null")
    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;


    public Hunting(Member member, Fish fish, Competition competition, int nombreOffish) {
        this.member = member;
        this.fish = fish;
        this.competition = competition;
        this.nombreOffish = nombreOffish;
    }

    public int getPoints() {
        if (this.fish != null && this.fish.getLevel() != null) {
            int baseScore = this.fish.getLevel().getPoints();
            int nombreOffish = this.nombreOffish;
            double logMultiplier = Math.log1p(nombreOffish);
            return (int) (baseScore * logMultiplier);
        }
        return 0;
    }
}
