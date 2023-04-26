package com.artisan.springsocialloginjwt.service;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenService {
    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;

    public String extractUserId(String jwt) {
        return decode(jwt);
    }

    public boolean isTokenValid(String jwt, UserDetails user) {
        return false;
    }

    public String generateToken(final Long id) {
        var now = Instant.now();
        var expiry = 36000L;

        var claims = JwtClaimsSet.builder()
                .issuer("artisan")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject("subject")
                .claim("id", id)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String decode(String accessToken) {
        var claims = jwtDecoder.decode(accessToken).getClaims();
        return claims.get("accountId").toString();
    }

    public boolean validateToken(final String token) {
        try {
            this.decode(token);
            return true;
        } catch (BadJwtException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        }
        return false;
    }
}

