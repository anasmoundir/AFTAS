package com.example.demo.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "level")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", nullable = false)
    private Long id;
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    @Column(name = "description")
    private String description;

    @PositiveOrZero(message = "Points must be a positive or zero integer")
    @Column(name = "points")
    private int points;
    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private Set<Fish> fishes;
    public Level(String description, int points) {
        this.description = description;
        this.points = points;
    }
}
