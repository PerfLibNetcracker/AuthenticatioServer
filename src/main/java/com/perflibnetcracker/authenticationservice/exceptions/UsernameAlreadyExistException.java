package com.perflibnetcracker.authenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class UsernameAlreadyExistException extends AuthenticationException {
    public UsernameAlreadyExistException(String msg) {
        super(msg);
    }

    public UsernameAlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
