package com.example.bookandauthorservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class BookDoesNotExistException extends RuntimeException {
    public BookDoesNotExistException(int bookId) {
        super("Book with id: [" + bookId + "] does not exist");
    }
}
