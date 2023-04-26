package com.artisan.springsocialloginjwt.api.auth;


import com.artisan.springsocialloginjwt.payload.request.LoginRequest;
import com.artisan.springsocialloginjwt.payload.request.SignupRequest;
import com.artisan.springsocialloginjwt.payload.response.LoginResponse;
import com.artisan.springsocialloginjwt.payload.response.SignupResponse;
import com.artisan.springsocialloginjwt.service.AuthenticationService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationApi {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @PermitAll
    public ResponseEntity<SignupResponse> signup(@RequestBody  SignupRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signup(signupRequest));
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}