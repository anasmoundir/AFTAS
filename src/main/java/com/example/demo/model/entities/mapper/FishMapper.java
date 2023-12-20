package com.example.demo.model.entities.mapper;

import com.example.demo.model.entities.Fish;
import com.example.demo.model.entities.dto.FishDto;

import java.util.List;

public interface FishMapper {

    FishDto fishToFishDto(Fish fish);

    Fish fishDtoToFish(FishDto fishDto);

    List<FishDto> fishToFishDtos(List<Fish> fishList);
}
