package com.perflibnetcracker.authenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class UserAlreadyBoughtBookException extends Exception {
    public UserAlreadyBoughtBookException(String message) {
        super(message);
    }

    public UserAlreadyBoughtBookException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
