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

    Hunting createHunting(Member member, Fish fish);

    HuntingDto updateHunting(Long id, HuntingDto huntingDto);

    HuntingDto addHuntingAndCalculateScore(HuntingDto huntingDto);

    void deleteHunting(Long id);

    HuntingDto createHunting(HuntingDto huntingDto);
}
