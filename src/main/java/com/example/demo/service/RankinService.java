package com.example.demo.service;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.Rankin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RankinService {
    void updateRankingAndScore(Hunting hunting);

    List<Rankin> getPodiumForCompetition(Competition competition);
}
