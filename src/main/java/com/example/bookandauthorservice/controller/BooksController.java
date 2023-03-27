package com.example.bookandauthorservice.controller;

import com.example.bookandauthorservice.model.Book;
import com.example.bookandauthorservice.model.BookDto;
import com.example.bookandauthorservice.service.IAuthorService;
import com.example.bookandauthorservice.service.IBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BooksController {
    private final IBooksService booksService;
    private final IAuthorService authorService;

    @GetMapping
    Collection<Book> getBooks() {
        return booksService.getBooks();
    }

    @GetMapping("/{id}")
    Book getBook(@PathVariable("id") int id) {
        return booksService.getBook(id);
    }

    @GetMapping("/author/{authorId}")
    List<Book> getByAuthorId(@PathVariable int authorId) {
        return booksService.getByAuthorId(authorId);
    }

    @PostMapping
    Book create(@RequestBody BookDto bookDto) {
        return booksService.create(bookDto);
    }

    @PutMapping("/{id}")
    Book update(@PathVariable int id, @RequestBody BookDto bookDto) {
        return booksService.update(id, bookDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        booksService.delete(id);
    }
}
