package com.example.demo.service.serviceImp;
import com.example.demo.configuration.configServices.JwtService;
import com.example.demo.model.entities.Role;
import com.example.demo.model.entities.User;
import com.example.demo.model.entities.dto.AuthRequestDTO;
import com.example.demo.model.entities.dto.JwtResponseDTO;
import com.example.demo.model.entities.dto.SignUpDto;
import com.example.demo.repository.IroleRepo;
import com.example.demo.repository.IuserRepo;
import com.example.demo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private IuserRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IroleRepo iroleRepo;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public void registerUser(SignUpDto signupdto) {
        User user = new User();
        user.setEnabled(false);
        user.setUsername(signupdto.getUsername());
        user.setEmail(signupdto.getEmail());
        user.setPassword(passwordEncoder.encode(signupdto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = iroleRepo.findByName("ADHERENT");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ADHERENT");
            iroleRepo.save(userRole);
        }
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }
    @Override
    public List<User> getDeactivatedUsers() {
        return userRepository.findByEnabledFalse();
    }
    @Override
    public boolean activateUser(String username) {
        if(userRepository.findByUsername(username) == null){
            return false;
        }else {
            userRepository.updateUserEnabledStatus(true,username);
            return true;
        }
    }

    @Override
    public boolean isEnable(String username) {
        User user = userRepository.findByUsername(username);
        return user.isEnabled();
    }
    @Override
    public JwtResponseDTO authenticateUser(AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );

            if (!isEnable(authRequestDTO.getUsername())) {
                String activationMessage = "User account is not activated. Please check your email for activation instructions.";
                throw new AuthenticationException(activationMessage) {};
            }

            if (authentication.isAuthenticated()) {
                logUserInfo(authentication);

                User user = userRepository.findByUsername(authRequestDTO.getUsername());
                String role = getHighestRole(user.getRoles());
                String generatedToken = jwtService.generateToken(authRequestDTO.getUsername(), role);
                String refreshToken = jwtService.generateRefreshToken(authRequestDTO.getUsername(), role);

                return JwtResponseDTO.builder().accessToken(generatedToken).refreshToken(refreshToken).build();
            } else {
                String errorMessage = "Invalid user credentials.";
                throw new AuthenticationException(errorMessage) {};
            }
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage()) {};
        }
    }

    private void logUserInfo(Authentication authentication) {
        String username = authentication.getName();
        System.out.println("User logged in: " + username);


        System.out.println("User authorities (roles):");
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            System.out.println("- " + authority.getAuthority());
        }

    }

    private String getHighestRole(Set<Role> roles) {
        String highestRole = "ADHERENT";
        for (Role role : roles) {
            if (role.getName().equals("MANAGER")) {
                highestRole = "MANAGER";
                break;
            } else if (role.getName().equals("JURY")) {
                if (!highestRole.equals("MANAGER")) {
                    highestRole = "JURY";
                }
            }
        }
        return highestRole;
    }

}
