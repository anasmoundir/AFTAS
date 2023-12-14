package com.example.demo.model.entities;

import jakarta.persistence.*;
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
    @Column(name = "description")
    private String description;
    @Column(name = "points")
    private int points;
    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private Set<Fish> fishes;
    public Level(String description, int points) {
        this.description = description;
        this.points = points;
    }
}
