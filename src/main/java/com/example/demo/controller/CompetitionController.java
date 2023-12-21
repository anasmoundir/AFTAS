package com.example.demo.controller;

import com.example.demo.model.entities.dto.CompetitionDto;
import com.example.demo.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competition")
@CrossOrigin(origins = "http://localhost:4200")
public class CompetitionController {
    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionDto> getCompetitionById(@PathVariable Long id) {
        CompetitionDto competitionDto = competitionService.getCompetitionById(id);
        return ResponseEntity.ok(competitionDto);
    }

    @GetMapping
    public ResponseEntity<Page<CompetitionDto>> getAllCompetitions(Pageable pageable) {
        Page<CompetitionDto> competitionDtos = competitionService.getAllCompetitions(pageable);
        return ResponseEntity.ok(competitionDtos);
    }

    @PostMapping
    public ResponseEntity<CompetitionDto> addCompetition(@RequestBody CompetitionDto competitionDto) {
        CompetitionDto newCompetitionDto = competitionService.addCompetition(competitionDto);
        return ResponseEntity.ok(newCompetitionDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCompetition(@PathVariable Long id, @RequestBody CompetitionDto competitionDto) {
        competitionService.updateCompetition(id, competitionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/open")
    public ResponseEntity<Page<CompetitionDto>> getOpenCompetitions(Pageable pageable) {
        Page<CompetitionDto> openCompetitions = competitionService.getOpenCompetitions(pageable);
        return new ResponseEntity<>(openCompetitions, HttpStatus.OK);
    }
    @GetMapping("/closed")
    public ResponseEntity<Page<CompetitionDto>> getClosedCompetitions(Pageable pageable) {
        Page<CompetitionDto> closedCompetitions = competitionService.getClosedCompetitions(pageable);
        return new ResponseEntity<>(closedCompetitions, HttpStatus.OK);
    }

}
