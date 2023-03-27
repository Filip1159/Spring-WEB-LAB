package com.example.bookandauthorservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Authorship {
    private int authorId;
    private int bookId;
}
