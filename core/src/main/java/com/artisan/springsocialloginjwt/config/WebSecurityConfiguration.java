package com.artisan.springsocialloginjwt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain doFilter(HttpSecurity http) throws Exception {
        http.csrf().disable() // rest api 라서 사용 안함
                .cors().and() // 세션을 사용하지 않고 jwt 토큰을 활용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authenticationProvider(authenticationProvider);
//        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
