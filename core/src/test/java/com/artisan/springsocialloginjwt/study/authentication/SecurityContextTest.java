package com.artisan.springsocialloginjwt.study.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

public class SecurityContextTest {


    @Test
    void 시큐리티_테스트(){
        /*
         * By default, SecurityContextHolder uses a ThreadLocal to store these details,
         * which means that the SecurityContext is always available to methods in the same thread,
         * even if the SecurityContext is not explicitly passed around as an argument to those methods.
         * Using a ThreadLocal in this way is quite safe if you take care to clear the thread after the present principal’s request is processed.
         * Spring Security’s FilterChainProxy ensures that the SecurityContext is always cleared.
         *
         * 시큐리티는 단일 스레드에서 동작하기 때문에 다른 스레드에서 접근하거나 중복되서 사용되지 않는다.
         * 예상으로는 하나의 요청은 단일 스레드에서 실행되기 때문에 요청과 응답을 처리하면서 시큐리티가 적용되는 영역 외에는 다른 요청에서 같은 시큐리티 컨텍스트 Authentication 이 침범하지 않을 것으로 예상됨
         */
        var context = SecurityContextHolder.getContext();
        // A more common production scenario is UsernamePasswordAuthenticationToken(userDetails, password, authorities).
        // 테스트 용으로 간단한 TestingAuthenticationToken 으로 설정함
        Authentication authentication = new TestingAuthenticationToken("username", "password", "ROLE_USER");
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        final var otherContext = SecurityContextHolder.getContext();

        System.out.println(otherContext.getAuthentication());

        assertThat(otherContext.getAuthentication()).isSameAs(authentication); // 한번 등록한 컨텍스트는 다른 컨텍스트와 동일함


        // 의문점1 다른 컨텍스트가 추가 될 경우 이전 컨텍스트와 동일하면 같은 컨텍스트 일까?

        Authentication authentication1 = new TestingAuthenticationToken("username", "password", "ROLE_USER");
        Authentication authentication2 = new TestingAuthenticationToken("username","password1","ROLE_USER");

        assertThat(otherContext.getAuthentication()).isEqualTo(authentication1); // 멤버의 값이 동일하면 같은 authentication으로 취급함
        assertThat(otherContext.getAuthentication()).isNotEqualTo(authentication2); // 멤버의 값이 다르면 다른 authentication이다.

        context.setAuthentication(authentication2); // authentication1 에서 2로 교체했음

        assertThat(otherContext.getAuthentication()).isEqualTo(authentication1);  // 교체가 되었기 때문에 다른 authentication임

    }
}
