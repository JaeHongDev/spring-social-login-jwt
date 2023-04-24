package com.artisan.springsocialloginjwt.api.auth;


import com.artisan.springsocialloginjwt.payload.request.SignupRequest;
import com.artisan.springsocialloginjwt.payload.response.SignupResponse;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationApi {
    @PostMapping("/signup")
    @PermitAll
    public ResponseEntity<SignupResponse> signup(@RequestBody  SignupRequest signupRequest){
        return ResponseEntity.ok(new SignupResponse("token"));
    }
}