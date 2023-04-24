package com.artisan.springsocialloginjwt.exception;

import java.util.concurrent.ExecutionException;
import lombok.Getter;


@Getter
public class DomainException extends RuntimeException{
    private final Integer id;

    public DomainException(DomainExceptionCode domainExceptionCode) {
        super(domainExceptionCode.getMessage());
        this.id= domainExceptionCode.getId();
    }
}
