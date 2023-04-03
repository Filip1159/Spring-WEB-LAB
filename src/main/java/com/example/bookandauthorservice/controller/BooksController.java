package com.example.bookandauthorservice.controller;

import com.example.bookandauthorservice.model.Author;
import com.example.bookandauthorservice.model.Book;
import com.example.bookandauthorservice.model.BookWithAuthors;
import com.example.bookandauthorservice.model.BookRequest;
import com.example.bookandauthorservice.service.IAuthorService;
import com.example.bookandauthorservice.service.IBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BooksController {
    private final IBooksService booksService;
    private final IAuthorService authorService;

    @GetMapping
    Collection<BookWithAuthors> getBooks() {
        return booksService.getBooksWithAuthors();
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
    ResponseEntity<BookWithAuthors> create(@RequestBody BookRequest bookRequest) {
        bookRequest.authorIds().forEach(authorService::getById); // validate that all authors exist
        var createdBook = booksService.create(bookRequest);
        var response = new BookWithAuthors(
                createdBook.getId(),
                createdBook.getTitle(),
                authorService.getByBookId(createdBook.getId()).stream().map(Author::getId).toList(),
                createdBook.getPages()
        );
        return ResponseEntity.status(CREATED).body(response);
    }

    @PutMapping("/{id}")
    BookWithAuthors update(@PathVariable int id, @RequestBody BookRequest bookRequest) {
        bookRequest.authorIds().forEach(authorService::getById);  // check if all authors exist
        var updatedBook = booksService.update(id, bookRequest);
        return new BookWithAuthors(
                updatedBook.getId(),
                updatedBook.getTitle(),
                authorService.getByBookId(updatedBook.getId()).stream().map(Author::getId).toList(),
                updatedBook.getPages()
        );
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        booksService.delete(id);
    }
}
