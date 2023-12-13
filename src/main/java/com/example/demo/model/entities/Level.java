package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "level")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "code")
    private int code;
    @Column(name = "description")
    private String descirption;
    @Column(name = "points")
    private int points;
    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private Set<Fish> fishes;
}
