package com.example.bookandauthorservice.controller;

import com.example.bookandauthorservice.model.Author;
import com.example.bookandauthorservice.model.AuthorDto;
import com.example.bookandauthorservice.service.IAuthorService;
import com.example.bookandauthorservice.service.IBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final IAuthorService authorService;
    private final IBooksService booksService;

    @GetMapping("/{id}")
    Author getById(@PathVariable int id) {
        return authorService.getById(id);
    }

    @GetMapping
    List<Author> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/book/{bookId}")
    List<Author> getByBookId(@PathVariable int bookId) {
        return authorService.getByBookId(bookId);
    }

    @PostMapping
    ResponseEntity<Author> create(@RequestBody AuthorDto authorDto) {
        authorDto.bookIds().forEach(booksService::getBook);  // validate all books exist
        return ResponseEntity.status(CREATED).body(authorService.create(authorDto));
    }

    @PutMapping("/{id}")
    Author update(@PathVariable int id, @RequestBody AuthorDto authorDto) {
        authorDto.bookIds().forEach(booksService::getBook);  // validate all books exist
        return authorService.update(id, authorDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        authorService.delete(id);
    }
}
