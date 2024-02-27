package com.example.demo.controller;
import com.example.demo.configuration.configServices.JwtService;
import com.example.demo.model.entities.User;
import com.example.demo.model.entities.dto.AuthRequestDTO;
import com.example.demo.model.entities.dto.JwtResponseDTO;
import com.example.demo.model.entities.dto.LoginDto;
import com.example.demo.model.entities.dto.SignUpDto;
import com.example.demo.repository.IuserRepo;
import com.example.demo.service.RegistrationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:4200")
public class HomeController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IuserRepo userRepository;

    @Autowired
    private RegistrationService registrationService;
    @Resource
    private JwtService jwtService;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            JwtResponseDTO response = registrationService.authenticateUser(authRequestDTO);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signupdto) {
        if (userRepository.existsByUsername(signupdto.getUsername())) {
            return new ResponseEntity<>("Username is already exist!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signupdto.getEmail())) {
            return new ResponseEntity<>("Email is already exist!", HttpStatus.BAD_REQUEST);
        }
        registrationService.registerUser(signupdto);
        String successMessage = "User registered successfully!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestBody LoginDto loginDto) {

      if (registrationService.activateUser(loginDto.getUsername()))
      {
          return new ResponseEntity<>("User activated successfully", HttpStatus.OK);
      }
      else
      {
          return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
      }

    }

    @GetMapping("/deactivated")
    public ResponseEntity<List<User>> getDeactivatedUsers() {
        List<User> deactivatedUsers = registrationService.getDeactivatedUsers();
        return ResponseEntity.ok(deactivatedUsers);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponseDTO> refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String refreshToken = httpServletRequest.getHeader("Refresh-Token");

        if (refreshToken == null) {
            JwtResponseDTO responseDTO = JwtResponseDTO.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

        String newAccessToken = jwtService.generateAccessTokenFromRefreshToken(refreshToken);

        if (newAccessToken != null) {
            JwtResponseDTO responseDTO = JwtResponseDTO.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(null)
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        } else {
            JwtResponseDTO responseDTO = JwtResponseDTO.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @PostMapping("/logout")
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}
