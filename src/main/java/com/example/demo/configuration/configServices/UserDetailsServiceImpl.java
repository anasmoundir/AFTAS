package com.example.demo.configuration.configServices;

import com.example.demo.model.entities.User;
import com.example.demo.repository.IuserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IuserRepo userRepository;


    static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) {
        Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountLocked(!user.isEnabled())
                .build();
    }
}
