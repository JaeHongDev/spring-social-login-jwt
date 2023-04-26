package com.example.securitystudytest.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.web.context.support.GenericWebApplicationContext;


@SpringBootTest
public class JwtTokenTest {

    @Autowired
    JwtEncoder jwtEncoder;
    @Autowired
    JwtDecoder jwtDecoder;

    @Test
    void 인코더_테스트() throws JOSEException {
        var now = Instant.now();
        var expiry = 36000L;

        var claims = JwtClaimsSet.builder()
                .issuer("artisan")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject("subject")
                .claim("id", 1L)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        System.out.println(token);
        var id = (Long) jwtDecoder.decode(token).getClaims().get("id");

        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 디코딩_만료시간_초과() throws JOSEException {
        var now = Instant.now();
        var expiry = 36000L;

        var claims = JwtClaimsSet.builder()
                .issuer("artisan")
                .issuedAt(now.minusSeconds(expiry * 2))
                .expiresAt(now.minusSeconds(expiry))
                .subject("subject")
                .claim("id", 1L)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        System.out.println(token);
        Assertions.assertThatThrownBy(()->{
            var id = (Long) jwtDecoder.decode(token).getClaims().get("id");
            assertThat(id).isEqualTo(1L);
        }).isInstanceOf(JwtValidationException.class);

    }

    @Test
    void 잘못된_퍼블릭키로_검증(){
        var token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhcnRpc2FuIiwic3ViIjoic3ViamVjdCIsImlkIjoxLCJleHAiOjE2ODI1NDA0NDcsImlhdCI6MTY4MjUwNDQ0N30.0efiwdg2bR0S2XdpBtBXbGz8myZDIy7ZLmPJbG7c8sY0VMM43XRBbkMypswUJFB1O1eCCnaksH2pIEwNXkJm_eQmDdFXd2nK25ihCfsl4EXDnhghpSrPwcTASCfCjFctkSZ-QysJKvm0GDeYbSVIJsj9lezKgw0BW2l8WvUhk6Gepi7fhw8cp7rRXyzPgxRDfMOFZnYYzmdHV3a7Nd6E-AKxvI8KwQTfHRXNhGIuEW5gxpaGoyAsgJU19f4qzZqNHLZv5F4OovYAzhmWqz1bF3Ig1CapBZFIO43xxqRY2VQqzhm2jVyOQiyMSTs3tB1hXzBqt9PxLQwNHC6u5Fa60A";
        //Assertions.assertThatThrownBy(()->{
            var id = (Long) jwtDecoder.decode(token).getClaims().get("id");
            assertThat(id).isEqualTo(1L);
        //}).isInstanceOf(JwtValidationException.class);
    }
}
