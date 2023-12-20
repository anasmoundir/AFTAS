package com.example.demo.service;

import com.example.demo.model.entities.dto.CompetitionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompetitionService {

    CompetitionDto getCompetitionById(Long id);

    List<CompetitionDto> getAllCompetitions();

    CompetitionDto addCompetition(CompetitionDto competitionDto);

    void updateCompetition(Long id, CompetitionDto competitionDto);

    void deleteCompetition(Long id);

//    Page<CompetitionDto> getOpenCompetitions(Pageable pageable);

//    List<CompetitionDto> getOpenCompetitions();

    Page<CompetitionDto> getOpenCompetitions(Pageable pageable);

    Page<CompetitionDto> getClosedCompetitions(Pageable pageable);
}
