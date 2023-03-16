package com.example.bookandauthorservice.controller;

import com.example.bookandauthorservice.exception.UnknownAuthorException;
import com.example.bookandauthorservice.model.Book;
import com.example.bookandauthorservice.model.BookDto;
import com.example.bookandauthorservice.service.IAuthorService;
import com.example.bookandauthorservice.service.IBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

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
    Optional<Book> getByAuthorId(@PathVariable int authorId) {
        return booksService.getByAuthorId(authorId);
    }

    @PostMapping
    Book create(@RequestBody BookDto bookDto) {
        if (authorService.getById(bookDto.authorId()) != null) {
            return booksService.create(bookDto);
        } else {
            throw new UnknownAuthorException(bookDto.authorId());
        }
    }

    @PutMapping("/{id}")
    Book update(@PathVariable int id, @RequestBody BookDto bookDto) {
        if (authorService.getById(bookDto.authorId()) != null) {
            return booksService.update(id, bookDto);
        } else {
            throw new UnknownAuthorException(bookDto.authorId());
        }
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        booksService.delete(id);
    }
}
