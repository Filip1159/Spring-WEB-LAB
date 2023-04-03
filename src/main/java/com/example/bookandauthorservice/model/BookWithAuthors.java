package com.example.bookandauthorservice.model;

import java.util.List;

public record BookWithAuthors(
        int id,
        String title,
        List<Integer> authorIds,
        int pages
) {
}
