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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
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
    public JwtResponseDTO authenticateUser(@RequestBody AuthRequestDTO authRequestDTO)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );
            System.out.println("Authentication:  " + authentication.getAuthorities());

            if(registrationService.isEnable(authRequestDTO.getUsername()))
            {
                throw new UsernameNotFoundException("User not activated");
            }

            if (authentication.isAuthenticated())
            {
                String role = authentication.getAuthorities().toString();
                String generatedToken = jwtService.generateToken(authRequestDTO.getUsername(), role);
                return JwtResponseDTO.builder().accessToken(generatedToken).build();
            } else {
                throw new UsernameNotFoundException("Invalid user credentials");
            }

        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Invalid user credentials", e);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signupdto) {
        if (userRepository.existsByUsername(signupdto.getUsername())) {
            return new ResponseEntity<>("Username is already exist!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signupdto.getEmail())) {
            return new ResponseEntity<>("Email is already exist!", HttpStatus.BAD_REQUEST);
        } else {
            registrationService.registerUser(signupdto);
        }
        return new  ResponseEntity<>("User registered successfully",HttpStatus.OK);
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
}
