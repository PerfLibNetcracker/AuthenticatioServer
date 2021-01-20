package com.perflibnetcracker.authenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class UserAlreadyHasSubscriptionException extends Exception {
    public UserAlreadyHasSubscriptionException(String message) {
        super(message);
    }

    public UserAlreadyHasSubscriptionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
