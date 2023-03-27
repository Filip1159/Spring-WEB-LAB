package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.model.Author;
import com.example.bookandauthorservice.model.AuthorDto;

import java.util.List;

public interface IAuthorService {
    Author getById(int authorId);
    List<Author> getAll();
    Author create(AuthorDto authorDto);
    Author update(int id, AuthorDto authorDto);
    void delete(int id);
    List<Author> getByBookId(int bookId);
}
