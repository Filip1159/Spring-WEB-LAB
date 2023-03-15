package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.model.Book;

import java.util.Collection;

public interface IBooksService {
    Collection<Book> getBooks();
    Book getBook(int id);
}
