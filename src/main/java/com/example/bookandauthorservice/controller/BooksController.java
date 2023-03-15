package com.example.bookandauthorservice.controller;

import com.example.bookandauthorservice.model.Book;
import com.example.bookandauthorservice.service.IBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class BooksController {
    private final IBooksService booksService;

    @GetMapping("/get/books")
    public Collection<Book> getBooks() {
        return booksService.getBooks();
    }

    @GetMapping("/get/book/{id}")
    public Book getBook(@PathVariable("id") int id) {
        return booksService.getBook(id);
    }
}
