package com.artisan.springsocialloginjwt.study;

import com.artisan.springsocialloginjwt.api.auth.AuthenticationApi;
import com.artisan.springsocialloginjwt.service.AuthenticationService;
import com.artisan.springsocialloginjwt.study.PasswordEncoderTest.PasswordEncoderConfig.PasswordEncoderDecorator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;


public class PasswordEncoderTest {
    static class PasswordEncoderConfig{


        @Bean
        PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }


        //salt 기능을 추가한 커스텀 passwordEncoder 추가하기
        @Component
        @Primary
        static class PasswordEncoderDecorator implements PasswordEncoder {
            private final PasswordEncoder passwordEncoder;

            public PasswordEncoderDecorator(PasswordEncoder passwordEncoder) {
                this.passwordEncoder = passwordEncoder;
            }

            @Override
            public String encode(CharSequence rawPassword) {
                System.out.println("======");
                return "###"+passwordEncoder.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                System.out.println("======");
                return passwordEncoder.matches("###"+passwordEncoder, encodedPassword);
            }

            @Override
            public boolean upgradeEncoding(String encodedPassword) {
                return PasswordEncoder.super.upgradeEncoding(encodedPassword);
            }
        }
    }

    @Test
    void 비밀번호_생성_테스트(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();

        // passwordEncoder 는 빈으로 등록되어 있지 않음
        ac.registerBean(PasswordEncoderConfig.class);
        ac.registerBean(PasswordEncoderDecorator.class);
        ac.refresh();

        var bCryptPasswordEncoder = ac.getBean(PasswordEncoder.class);


        var password = bCryptPasswordEncoder.encode("1234");
        bCryptPasswordEncoder.matches("1234", password);
    }
}
