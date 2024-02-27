package com.example.demo.configuration.ConfigFilter;

import com.example.demo.configuration.configServices.JwtService;
import com.example.demo.configuration.configServices.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String accessToken = null;
        String refreshToken = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
            refreshToken = request.getHeader("Refresh-Token");
            try {
                username = jwtService.extractUsername(accessToken);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(accessToken, userDetails)) {
                // Extract authorities from token
                Collection<? extends GrantedAuthority> authorities = jwtService.extractAuthorities(accessToken);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else if (refreshToken != null) {
                String newAccessToken = jwtService.generateAccessTokenFromRefreshToken(refreshToken);

                if (newAccessToken != null) {
                    userDetails = userDetailsService.loadUserByUsername(username);
                    // Extract authorities from token
                    Collection<? extends GrantedAuthority> authorities = jwtService.extractAuthorities(newAccessToken);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    response.addHeader("Authorization", "Bearer " + newAccessToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}