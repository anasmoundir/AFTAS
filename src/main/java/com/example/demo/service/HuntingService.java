package com.example.demo.service;

import com.example.demo.model.entities.dto.HuntingDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HuntingService {
    List<HuntingDto> getAllHuntings();
    HuntingDto getHuntingById(Long id);
    HuntingDto createHunting(HuntingDto huntingDto);
    HuntingDto updateHunting(Long id, HuntingDto huntingDto);
    void deleteHunting(Long id);
}
