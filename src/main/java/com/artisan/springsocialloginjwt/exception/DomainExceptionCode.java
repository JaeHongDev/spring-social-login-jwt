package com.artisan.springsocialloginjwt.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DomainExceptionCode {
    AUTH(1000),
    EXISTS_USER_ID(AUTH.id + 1, "이미 존재하는 사용자 아이디입니다."),
    LOGIN_FAIL(AUTH.id + 2, "로그인에 실패했습니다."),
    USER(2000);

    private final Integer id;
    private final String message;

    DomainExceptionCode(Integer id) {
        this.id= id;
        this.message = "에러메시지가 없는 에러 타입입니다.";
    }


    public DomainException throwError(){
        return new DomainException(this);
    }

    public String getMessage(){
        return String.format("[code:%d] %s", id, message);
    }
    public Integer getId(){
        return this.id;

    }
}
