package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.exception.BookDoesNotExistException;
import com.example.bookandauthorservice.model.Book;
import com.example.bookandauthorservice.model.BookWithAuthors;
import com.example.bookandauthorservice.model.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BooksService implements IBooksService {
    private static final List<Book> booksRepo = new ArrayList<>();
    private final IAuthorshipService authorshipService;

    static {
        booksRepo.add(new Book(1, "Potop", 936));
        booksRepo.add(new Book(2, "Wesele", 150));
        booksRepo.add(new Book(3, "Dziady", 292));
        booksRepo.add(new Book(5, "Pan Tadeusz", 326));
        booksRepo.add(new Book(6, "Lalka", 617));
        booksRepo.add(new Book(7, "Makbet", 189));
        booksRepo.add(new Book(8, "Romeo i Julia", 134));
        booksRepo.add(new Book(9, "Krzyżacy", 539));
        booksRepo.add(new Book(10, "W pustyni i w puszczy", 342));
        booksRepo.add(new Book(11, "Zemsta", 259));
        booksRepo.add(new Book(12, "Kordian", 173));
        booksRepo.add(new Book(13, "Ferdydurke", 245));
        booksRepo.add(new Book(14, "Projektowanie baz danych", 342));
    }
    @Override
    public Collection<Book> getBooks() {
        return booksRepo;
    }

    @Override
    public Collection<BookWithAuthors> getBooksWithAuthors() {
        return booksRepo.stream()
                .map(book -> new BookWithAuthors(
                        book.getId(),
                        book.getTitle(),
                        authorshipService.getAuthorIdsForBookId(book.getId()),
                        book.getPages()))
                .toList();
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
    public Book create(BookRequest bookRequest) {
        var newBookId = nextId();
        var newBook = new Book(newBookId, bookRequest.title(), bookRequest.pages());
        booksRepo.add(newBook);
        bookRequest.authorIds().forEach(id -> authorshipService.saveNewAuthorship(id, newBookId));
        return newBook;
    }

    @Override
    public Book update(int id, BookRequest bookRequest) {
        var existingBook = getBook(id);
        existingBook.setTitle(bookRequest.title());
        var authorsOfExistingBook = authorshipService.getAuthorIdsForBookId(id);
        bookRequest.authorIds().stream()  // add new authors
                .filter(authorId -> !authorsOfExistingBook.contains(authorId))
                .forEach(authorId -> authorshipService.saveNewAuthorship(authorId, id));
        authorsOfExistingBook.stream()  // delete old authors
                .filter(authorId -> !bookRequest.authorIds().contains(authorId))
                .forEach(authorId -> authorshipService.deleteAuthorship(authorId, id));
        existingBook.setPages(bookRequest.pages());
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
