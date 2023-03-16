package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.model.Book;
import com.example.bookandauthorservice.model.BookDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BooksService implements IBooksService {
    private static final List<Book> booksRepo = new ArrayList<>();

    static {
        booksRepo.add(new Book(1, "Potop", 3, 936));
        booksRepo.add(new Book(2, "Wesele", 2, 150));
        booksRepo.add(new Book(3, "Dziady", 1, 292));
    }
    @Override
    public Collection<Book> getBooks() {
        return booksRepo;
    }

    @Override
    public Book getBook(int id) {
        return booksRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Optional<Book> getByAuthorId(int authorId) {
        return booksRepo.stream()
                .filter(book -> book.getAuthorId() == authorId)
                .findFirst();
    }

    @Override
    public Book create(BookDto bookDto) {
        var newBook = new Book(nextId(), bookDto.title(), bookDto.authorId(), bookDto.pages());
        booksRepo.add(newBook);
        return newBook;
    }

    @Override
    public Book update(int id, BookDto bookDto) {
        var existingBook = getBook(id);
        existingBook.setTitle(bookDto.title());
        existingBook.setAuthorId(bookDto.authorId());
        existingBook.setPages(bookDto.pages());
        return existingBook;
    }

    @Override
    public void delete(int id) {
        var existingBook = getBook(id);
        booksRepo.remove(existingBook);
    }

    private int nextId() {
        return booksRepo.stream()
                .map(Book::getId)
                .max(Comparator.comparingInt(id -> id))
                .orElse(0) + 1;
    }
}
