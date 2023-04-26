package com.example.securitystudytest.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtTokenConfiguration {

    RSAPublicKey key;
    RSAPrivateKey priv;
    public JwtTokenConfiguration( @Value("${jwt.public.key}") RSAPublicKey key,  @Value("${jwt.private.key}") RSAPrivateKey priv) {

        this.key = key;
        this.priv = priv;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() throws JOSEException {
        // RSA 키 생성 방법
        /*var generator = new RSAKeyGenerator(2048);
        var pk = generator.generate().toRSAPublicKey();
        var prk = generator.generate().toRSAPrivateKey();
        JWK jwk = new RSAKey.Builder(pk).privateKey(prk).build();*/

        final var jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }
}

