package com.example.securitystudytest.config;


import com.example.securitystudytest.component.jwt.JwtAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurityConfiguration {

    //private final SampleFilter sampleFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // rest api 라서 사용 안함
                .cors().and() // 세션을 사용하지 않고 jwt 토큰을 활용
                .addFilter(jwtAuthenticationFilter())
                .authenticationProvider(preAuthenticatedAuthenticationProvider())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager(){
        return authentication -> {
            // 여기서 권한을 부여해야 함
            var context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            return authentication;
        };
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter("Authorization");
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(token -> {
            String username = extractUsernameFromJwt(token);
            return new User(username, "", List.of(new SimpleGrantedAuthority("USER")));
        });
        return provider;
    }

    private String extractUsernameFromJwt(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) {
        return null;
    }

    private String extractUsernameFromJwt(String token) {
        // JWT에서 사용자 이름 추출하는 로직
        // ...
        return token;
    }

//    @Component
//    @Slf4j
//    static class SampleFilter extends OncePerRequestFilter {
//        @Override
//        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//                throws ServletException, IOException {
//            log.info("request");
//            var context = SecurityContextHolder.createEmptyContext();
//            context.setAuthentication(new TestingAuthenticationToken("world","","USER"));
//            SecurityContextHolder.setContext(context);
//
//            filterChain.doFilter(request,response);
//        }
//    }

}
