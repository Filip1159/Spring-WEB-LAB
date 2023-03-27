package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.exception.BookDoesNotExistException;
import com.example.bookandauthorservice.model.Book;
import com.example.bookandauthorservice.model.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BooksService implements IBooksService {
    private static final List<Book> booksRepo = new ArrayList<>();
    private final IAuthorService authorService;
    private final IAuthorshipService authorshipService;

    static {
        booksRepo.add(new Book(1, "Potop", 936));
        booksRepo.add(new Book(2, "Wesele", 150));
        booksRepo.add(new Book(3, "Dziady", 292));
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
                .orElseThrow(() -> new BookDoesNotExistException(id));
    }

    @Override
    public List<Book> getByAuthorId(int authorId) {
        var bookIdsForAuthor = authorshipService.getBookIdsForAuthorId(authorId);
        return bookIdsForAuthor.stream()
                .map(this::getBook)
                .toList();
    }

    @Override
    public Book create(BookDto bookDto) {
        var newBookId = nextId();
        var newBook = new Book(newBookId, bookDto.title(), bookDto.pages());
        booksRepo.add(newBook);
        bookDto.authorIds().forEach(authorService::getById);
        bookDto.authorIds().forEach(id -> authorshipService.saveNewAuthorship(id, newBookId));
        return newBook;
    }

    @Override
    public Book update(int id, BookDto bookDto) {
        var existingBook = getBook(id);
        existingBook.setTitle(bookDto.title());
        bookDto.authorIds().forEach(authorService::getById);  // check if all authors exist
        var authorsOfExistingBook = authorshipService.getAuthorIdsForBookId(id);
        bookDto.authorIds().stream()  // add new authors
                .filter(authorId -> !authorsOfExistingBook.contains(authorId))
                .forEach(authorId -> authorshipService.saveNewAuthorship(authorId, id));
        authorsOfExistingBook.stream()  // delete old authors
                .filter(authorId -> !bookDto.authorIds().contains(authorId))
                .forEach(authorId -> authorshipService.deleteAuthorship(authorId, id));
        existingBook.setPages(bookDto.pages());
        return existingBook;
    }

    @Override
    public void delete(int id) {
        var existingBook = getBook(id);
        authorshipService.getAuthorIdsForBookId(id).forEach(
                authorId -> authorshipService.deleteAuthorship(authorId, id)
        );
        booksRepo.remove(existingBook);
    }

    private int nextId() {
        return booksRepo.stream()
                .map(Book::getId)
                .max(Comparator.comparingInt(id -> id))
                .orElse(0) + 1;
    }
}
