package com.example.demo.service;

import com.example.demo.model.entities.dto.CompetitionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompetitionService {
    CompetitionDto getCompetitionById(Long id);

    List<CompetitionDto> getAllCompetitions();

    CompetitionDto addCompetition(CompetitionDto competitionDto);

    void updateCompetition(Long id, CompetitionDto competitionDto);

    void deleteCompetition(Long id);

    List<CompetitionDto> getOpenCompetitions();

    List<CompetitionDto> getClosedCompetitions();

}
