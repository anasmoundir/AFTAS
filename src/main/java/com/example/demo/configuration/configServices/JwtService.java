package com.example.demo.configuration.configServices;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.Claims;

import static com.example.demo.configuration.configServices.UserDetailsServiceImpl.logger;

@Component
public class JwtService {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${secret-key}")
    private String SECRET;

    @Value("${access-token-expiration-time}")
    private long JwtExpiration;

    @Value("${refresh-token-expiration-time}")
    private long refreshTokenExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid JWT token: " + e.getMessage());
        }
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateRefreshToken(String username, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username, refreshTokenExpiration);
    }
    public String generateAccessTokenFromRefreshToken(String refreshToken) {
        try {
            Claims claims = extractAllClaims(refreshToken);
            String username = claims.getSubject();
            String role = (String) claims.get("role");
            return createToken(new HashMap<>(), username, JwtExpiration);
        } catch (Exception e) {
            return null;
        }
    }


    public String generateToken(String username, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username, JwtExpiration);
    }



    private String createToken(Map<String, Object> claims, String username, long expirationTime) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        String role = (String) claims.get("role");
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role));
        logger.debug("Authorities extracted from token: {}", authorities);
        return authorities;
    }
}
