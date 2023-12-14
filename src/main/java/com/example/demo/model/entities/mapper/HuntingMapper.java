package com.example.demo.model.entities.mapper;

import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.dto.HuntingDto;

import java.util.List;

public interface HuntingMapper {
    HuntingDto HuntingToHuntingDto(Hunting hunting);
    Hunting HuntingDtoToHunting(HuntingDto huntingDto);
    List<HuntingDto> HuntingsToHuntingDto(List<Hunting> huntings);
}
