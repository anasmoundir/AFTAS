package com.example.demo.service;

import com.example.demo.model.entities.dto.AuthRequestDTO;
import com.example.demo.model.entities.dto.JwtResponseDTO;
import com.example.demo.model.entities.dto.SignUpDto;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {
    void registerUser(SignUpDto signupdto);

    boolean activateUser(String username);

    boolean isEnable(String username);

    JwtResponseDTO authenticateUser(AuthRequestDTO authRequestDTO);
}
