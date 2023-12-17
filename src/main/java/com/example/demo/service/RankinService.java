package com.example.demo.service;

import com.example.demo.model.entities.Hunting;
import org.springframework.stereotype.Service;

@Service
public interface RankinService {
    void updateRankingAndScore(Hunting hunting);
}
