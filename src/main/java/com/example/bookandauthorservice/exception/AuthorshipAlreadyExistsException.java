package com.example.bookandauthorservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class AuthorshipAlreadyExistsException extends RuntimeException {
    public AuthorshipAlreadyExistsException(String message) {
        super(message);
    }
}
