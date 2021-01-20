package com.perflibnetcracker.authenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NoBookRatingException extends Exception {
    public NoBookRatingException(String message) {
        super(message);
    }

    public NoBookRatingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
