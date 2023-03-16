package com.example.bookandauthorservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    private int id;
    private String name;
    private String surname;
}
