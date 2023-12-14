package com.example.demo.model.entities.dto;

import com.example.demo.model.entities.Competition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HuntingDto {
 private int nombreOffish;
 private Long competitionId;
 private Long fishId;
 private Long memberId;
}