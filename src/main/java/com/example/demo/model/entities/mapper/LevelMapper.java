package com.example.demo.model.entities.mapper;

import com.example.demo.model.entities.dto.LevelDto;

public interface LevelMapper {
    LevelDto levelToLevelDto(com.example.demo.model.entities.Level level);

    com.example.demo.model.entities.Level levelDtoToLevel(LevelDto levelDto);
}
