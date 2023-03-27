package com.example.bookandauthorservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class CannotDeleteAuthorException extends RuntimeException {
    public CannotDeleteAuthorException(int authorId, List<Integer> bookId) {
        super("Cannot delete author with id = [" + authorId +
                "], because it is in relation with books with ids: [" + bookId + "]");
    }
}
