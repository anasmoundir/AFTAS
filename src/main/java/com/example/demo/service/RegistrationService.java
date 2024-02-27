package com.example.demo.service;

import com.example.demo.model.entities.User;
import com.example.demo.model.entities.dto.AuthRequestDTO;
import com.example.demo.model.entities.dto.JwtResponseDTO;
import com.example.demo.model.entities.dto.SignUpDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegistrationService {
    void registerUser(SignUpDto signupdto);

    List<User> getDeactivatedUsers();

    boolean activateUser(String username);

    boolean isEnable(String username);

    JwtResponseDTO authenticateUser(AuthRequestDTO authRequestDTO);
}
