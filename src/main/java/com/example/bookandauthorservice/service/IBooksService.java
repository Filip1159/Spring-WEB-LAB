package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.model.Book;
import com.example.bookandauthorservice.model.BookDto;

import java.util.Collection;
import java.util.Optional;

public interface IBooksService {
    Collection<Book> getBooks();
    Book getBook(int id);
    Optional<Book> getByAuthorId(int authorId);
    Book create(BookDto bookDto);
    Book update(int id, BookDto bookDto);
    void delete(int id);
}
