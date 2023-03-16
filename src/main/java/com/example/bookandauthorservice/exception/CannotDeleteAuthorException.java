package com.example.bookandauthorservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class CannotDeleteAuthorException extends RuntimeException {
    public CannotDeleteAuthorException(int authorId, int bookId) {
        super("Cannot delete author with id = " + authorId +
                ", because it is in relation with book with id = " + bookId);
    }
}
