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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nombreOffish")
    private int nombreOffish;
    @OneToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;
    @OneToMany(mappedBy = "hunting", cascade = CascadeType.ALL)
    private Set<Fish> fishes;
}
