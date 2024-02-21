package com.example.demo.service.serviceImp;

import com.example.demo.model.entities.Role;
import com.example.demo.model.entities.User;
import com.example.demo.model.entities.dto.SignUpDto;
import com.example.demo.repository.IroleRepo;
import com.example.demo.repository.IuserRepo;
import com.example.demo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private IuserRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IroleRepo iroleRepo;
    @Override
    public void registerUser(SignUpDto signupdto) {
        User user = new User();
        user.setEnabled(false);
        user.setUsername(signupdto.getUsername());
        user.setEmail(signupdto.getEmail());
        user.setPassword(passwordEncoder.encode(signupdto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = iroleRepo.findByName("USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("USER");
            iroleRepo.save(userRole);
        }
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
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
}
