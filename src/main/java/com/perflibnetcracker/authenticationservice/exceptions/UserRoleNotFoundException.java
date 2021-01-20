package com.perflibnetcracker.authenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserRoleNotFoundException extends Exception {
    public UserRoleNotFoundException(String message) {
        super(message);
    }

    public UserRoleNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
