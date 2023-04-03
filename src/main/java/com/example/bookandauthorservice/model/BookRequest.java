package com.example.bookandauthorservice.model;

import java.util.List;

public record BookRequest(
        String title,
        int pages,
        List<Integer> authorIds
) {
}
