package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.model.Book;
import com.example.bookandauthorservice.model.BookWithAuthors;
import com.example.bookandauthorservice.model.BookRequest;

import java.util.Collection;
import java.util.List;

public interface IBooksService {
    Collection<Book> getBooks();

    Collection<BookWithAuthors> getBooksWithAuthors();

    Book getBook(int id);
    List<Book> getByAuthorId(int authorId);
    Book create(BookRequest bookRequest);
    Book update(int id, BookRequest bookRequest);
    void delete(int id);
}
