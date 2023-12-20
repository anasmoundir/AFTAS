package com.example.demo.model.entities.mapper;

import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.dto.HuntingDto;

import java.util.List;

public interface HuntingMapper {

    HuntingDto huntingToHuntingDto(Hunting hunting);

    Hunting huntingDtoToHunting(HuntingDto huntingDto);

    List<HuntingDto> huntingsToHuntingDtos(List<Hunting> huntings);
}
