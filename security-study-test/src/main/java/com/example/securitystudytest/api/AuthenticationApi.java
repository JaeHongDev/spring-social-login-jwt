package com.example.securitystudytest.api;


import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class AuthenticationApi {

    @GetMapping("/")
    @PermitAll
    public ResponseEntity<HelloResponse> hello(){
        return ResponseEntity.ok(new HelloResponse("world"));
    }


    @GetMapping("/me")
    @Secured({"USER"})
    public ResponseEntity<HelloResponse> helloMe(Authentication authentication){
        log.info("{}",authentication);

        return ResponseEntity.ok(new HelloResponse(authentication.getName()));
    }
}

record HelloResponse(String hello){

}
