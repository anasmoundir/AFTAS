package com.example.demo.configuration;


import com.example.demo.configuration.ConfigFilter.JwtAuthFilter;
import com.example.demo.configuration.configServices.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig
    {
        @Autowired
        JwtAuthFilter jwtAuthFilter;
        @Bean
        public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(Customizer.withDefaults())
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/api/auth/**").permitAll()

                            .requestMatchers(HttpMethod.GET, "/api/auth/deactivated").hasAuthority("MANAGER")
                            .requestMatchers(HttpMethod.POST, "/api/auth/activate").hasAuthority("MANAGER")

                            .requestMatchers("/api/member/All").hasAnyAuthority("MANAGER","JURY")
                            .requestMatchers(HttpMethod.POST, "/api/member").hasAuthority("MANAGER")
                            .requestMatchers(HttpMethod.GET, "/api/member/{id}").hasAnyAuthority("MANAGER","JURY")
                            .requestMatchers(HttpMethod.POST, "/api/member/{memberId}/competitions/{competitionId}/register").hasAuthority("JURY")
                            .requestMatchers(HttpMethod.PUT, "/api/member/{id}").hasAuthority("MANAGER")
                            .requestMatchers(HttpMethod.DELETE, "/api/member/{id}").hasAuthority("MANAGER")

                            .requestMatchers(HttpMethod.GET, "/api/competition").hasAnyAuthority("JURY", "ADHERENT", "MANAGER")
                            .requestMatchers(HttpMethod.GET, "/api/competition/open").hasAnyAuthority("JURY","MANAGER")
                            .requestMatchers(HttpMethod.POST, "/api/competition").hasAuthority("JURY")
                            .requestMatchers(HttpMethod.PUT, "/api/competition/{id}").hasAuthority("JURY")
                            .requestMatchers(HttpMethod.DELETE, "/api/competition/{id}").hasAuthority("JURY")
                            .anyRequest().authenticated()
                    )
                    .sessionManagement(AbstractHttpConfigurer::disable)
                    .sessionManagement(sessionManagement -> sessionManagement
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authenticationProvider(authenticationProvider())
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }
        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsService());
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            return authenticationProvider;
        }
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }
        @Bean
        public UserDetailsService userDetailsService()
        {
               return new UserDetailsServiceImpl();
        }

    }