package com.example.bookandauthorservice.model;

import lombok.*;

@Data
@AllArgsConstructor
public class Book {
    private final int id;
    private String title;
    private int pages;
}
