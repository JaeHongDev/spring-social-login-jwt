package com.artisan.springsocialloginjwt.service;


import com.artisan.springsocialloginjwt.entity.User;
import com.artisan.springsocialloginjwt.exception.DomainExceptionCode;
import com.artisan.springsocialloginjwt.payload.request.SignupRequest;
import com.artisan.springsocialloginjwt.payload.response.SignupResponse;
import com.artisan.springsocialloginjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest){
        this.userRepository.findByUserId(signupRequest.id()).ifPresent((user) -> {
            throw DomainExceptionCode.EXISTS_USER_ID.throwError();
        });

        var newUser = User.builder()
                .userId(signupRequest.id())
                .password(signupRequest.password())
                .name(signupRequest.name())
                .email(signupRequest.email())
                .build();

        var savedUser = userRepository.save(newUser);
        var token = tokenService.generateToken(savedUser.getId());

        return new SignupResponse(token);
    }
}
