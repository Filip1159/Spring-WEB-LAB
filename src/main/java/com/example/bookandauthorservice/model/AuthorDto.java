package com.example.bookandauthorservice.model;

import java.util.List;

public record AuthorDto(
        String name,
        String surname,
        List<Integer> bookIds
) {
}
