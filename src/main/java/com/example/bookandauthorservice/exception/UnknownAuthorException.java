package com.example.bookandauthorservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class UnknownAuthorException extends RuntimeException {
    public UnknownAuthorException(int authorId) {
        super("Author with provided id does not exist: " + authorId);
    }
}
