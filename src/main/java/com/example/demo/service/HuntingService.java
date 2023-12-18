package com.example.demo.service;

import com.example.demo.model.entities.Fish;
import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.dto.HuntingDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HuntingService {


    List<HuntingDto> getAllHuntings();

    HuntingDto getHuntingById(Long id);

    Hunting createHunting(HuntingDto huntingDto);

    HuntingDto addHuntingAndCalculateScore(HuntingDto huntingDto);

    HuntingDto updateHunting(Long id, HuntingDto huntingDto);
    void deleteHunting(Long id);
}
