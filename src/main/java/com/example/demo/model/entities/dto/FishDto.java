package com.example.demo.model.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FishDto {
    private Long id;
    private String name;
    private float averageweight;
    private LevelDto level;

}
