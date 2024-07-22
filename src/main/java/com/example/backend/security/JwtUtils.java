package com.example.backend.security;

import com.example.backend.model.user.UserDetailsImpl;
import com.example.backend.security.jwt.JwtConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtConfigProperties config;
    private final ObjectMapper objectMapper;

    public String generateJwtToken(final Authentication auth) {
        UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
        Instant issuedAt = Instant.now();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setClaims(buildUserClaims(principal))
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(issuedAt.plusMillis(config.getExpiration())))
                .signWith(HS512, config.getSecret())
                .compact();
    }

    private Claims buildUserClaims(final UserDetailsImpl userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("id", userDetails.getId());
        claims.put("username", userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        claims.put("lastName", userDetails.getLastName());
        claims.put("firstName", userDetails.getFirstName());
        claims.put("email", userDetails.getEmail());
        return claims;
    }

    public UserDetails getUserDetailsFromJwt(final String token) {
        Claims claims = Jwts.parser().setSigningKey(config.getSecret()).parseClaimsJws(token).getBody();
        return UserDetailsImpl.builder()
                .id(Long.valueOf(claims.get("id").toString()))
                .username((String) claims.get("username"))
                .email((String) claims.get("email"))
                .lastName((String) claims.get("lastName"))
                .firstName((String) claims.get("firstName"))
                .authorities(((List<String>) claims.get("roles")).stream().map(SimpleGrantedAuthority::new).toList())
                .build();
    }

    public boolean validateJwtToken(final String authToken) {
        try {
            Jwts.parser().setSigningKey(config.getSecret()).parseClaimsJws(authToken);
            return true;
        } catch (final Exception ex) {
            log.error("Invalid jwt", ex);
        }
        return false;
    }

}
