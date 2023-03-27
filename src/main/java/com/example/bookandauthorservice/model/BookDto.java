package com.example.bookandauthorservice.model;

import java.util.List;

public record BookDto(
        String title,
        List<Integer> authorIds,
        int pages
) {
}
