package com.artisan.springsocialloginjwt.api.user;


import com.artisan.springsocialloginjwt.payload.response.UserInformationResponse;
import org.hibernate.engine.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @GetMapping("/me")
    @Secured({})
    public ResponseEntity<UserInformationResponse> getMe(Authentication authentication){

        return ResponseEntity.ok(null);
    }
}
