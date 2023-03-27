package com.example.bookandauthorservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class AuthorDoesNotExistException extends RuntimeException {
    public AuthorDoesNotExistException(int authorId) {
        super("Author with id: [" + authorId + "] does not exist");
    }
}
