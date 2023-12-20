package com.example.demo.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fish")
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name")
    private String name;

    @Positive(message = "Average weight must be a positive number")
    @Column(name = "averageweight")
    private float averageweight;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;
}