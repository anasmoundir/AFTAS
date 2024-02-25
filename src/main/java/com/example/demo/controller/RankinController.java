package com.example.demo.controller;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Rankin;
import com.example.demo.model.entities.dto.RankinDto;
import com.example.demo.service.RankinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RankinController {
    private  final RankinService rankinService;
    @Autowired
    public RankinController(RankinService rankinService) {
        this.rankinService = rankinService;
    }

    @GetMapping("/podium/{competitionId}")
    @PreAuthorize("hasAnyRole('ADHERENT', 'JURY', 'MANAGER')")
    public ResponseEntity<List<RankinDto>> getPodiumForCompetition(@PathVariable Long competitionId) {
        Competition competition = new Competition();
        competition.setId(competitionId);
        List<RankinDto> podium = rankinService.getPodiumForCompetition(competition);
        if (podium.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(podium, HttpStatus.OK);
    }
}
