package com.example.bookandauthorservice.model;

public record BookDto(
        String title,
        int authorId,
        int pages
) {
}
