package com.example.securitystudytest.component.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class JwtAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final String tokenHeader;

    public JwtAuthenticationFilter(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (token == null || token.isEmpty()) {
            return null;
        }
        // JWT에서 사용자 이름 추출
        return extractUsernameFromJwt(token);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (token == null || token.isEmpty()) {
            return null;
        }
        // JWT를 credentials로 사용
        return token;
    }

    private String extractUsernameFromJwt(String token) {
        // JWT에서 사용자 이름 추출하는 로직
        // ...
        return token;
    }
}