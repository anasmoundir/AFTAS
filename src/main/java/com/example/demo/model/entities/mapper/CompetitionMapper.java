package com.example.demo.model.entities.mapper;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.dto.CompetitionDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface CompetitionMapper {
    CompetitionDto competitionToCompetitionDto(Competition competition);
    Competition competitionDtoToCompetition(CompetitionDto competitionDto);
    List<CompetitionDto> competitionsToCompetitionDtos(List<Competition> competitions);
}
