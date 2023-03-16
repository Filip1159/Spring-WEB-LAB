package com.example.bookandauthorservice.controller;

import com.example.bookandauthorservice.exception.CannotDeleteAuthorException;
import com.example.bookandauthorservice.model.Author;
import com.example.bookandauthorservice.model.AuthorDto;
import com.example.bookandauthorservice.service.IAuthorService;
import com.example.bookandauthorservice.service.IBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    Author create(@RequestBody AuthorDto authorDto) {
        return authorService.create(authorDto);
    }

    @PutMapping("/{id}")
    Author update(@PathVariable int id, @RequestBody AuthorDto authorDto) {
        return authorService.update(id, authorDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        var bookForAuthor = booksService.getByAuthorId(id);
        if (bookForAuthor.isPresent()) {
            throw new CannotDeleteAuthorException(id, bookForAuthor.get().getId());
        } else {
            authorService.delete(id);
        }
    }
}
