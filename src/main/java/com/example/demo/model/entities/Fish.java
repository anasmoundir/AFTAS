package com.example.demo.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="fish")
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "averageweight")
    private  float  averageweight;
    @ManyToOne
    @JoinColumn(name = "hunting_id")
    private Hunting hunting;
    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;
}
