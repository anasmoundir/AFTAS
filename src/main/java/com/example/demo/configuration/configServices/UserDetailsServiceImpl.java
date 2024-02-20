package com.example.demo.configuration.configServices;

import com.example.demo.model.entities.User;
import com.example.demo.repository.IuserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IuserRepo userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering login by username");
        User user = userRepository.findByUsername(username);
        logger.debug("User found: " + user);
        if (user == null) {
            logger.error("User not found: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        logger.info("User authenticated successfully: " + username);
        return new CustomUserDetails(user);
    }



}
