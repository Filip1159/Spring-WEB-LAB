package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.exception.AuthorDoesNotExistException;
import com.example.bookandauthorservice.model.Author;
import com.example.bookandauthorservice.model.AuthorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {
    private static final List<Author> authorRepo = new ArrayList<>();
    private final IAuthorshipService authorshipService;

    static {
        authorRepo.add(new Author(1, "Adam", "Mickiweicz"));
        authorRepo.add(new Author(2, "Stanisław", "Reymont"));
        authorRepo.add(new Author(3, "Henryk", "Sienkiweicz"));
        authorRepo.add(new Author(4, "William", "Szekspir"));
        authorRepo.add(new Author(5, "Bolesław", "Prus"));
        authorRepo.add(new Author(6, "Aleksander", "Fredro"));
        authorRepo.add(new Author(7, "Juliusz", "Słowacki"));
        authorRepo.add(new Author(8, "Witold", "Gombrowicz"));
        authorRepo.add(new Author(9, "Hanna", "Mazur"));
        authorRepo.add(new Author(10, "Zygmunt", "Mazur"));
    }

    @Override
    public Author getById(int authorId) {
        return authorRepo.stream()
                .filter(author -> author.getId() == authorId)
                .findAny()
                .orElseThrow(() -> new AuthorDoesNotExistException(authorId));
    }

    @Override
    public List<Author> getAll() {
        return authorRepo;
    }

    @Override
    public List<Author> getByBookId(int bookId) {
        var authorIdsForBook = authorshipService.getAuthorIdsForBookId(bookId);
        return authorIdsForBook.stream()
                .map(this::getById)
                .toList();
    }

    @Override
    public Author create(AuthorDto authorDto) {
        var newAuthorId = nextId();
        var newAuthor = new Author(newAuthorId, authorDto.name(), authorDto.surname());
        authorRepo.add(newAuthor);
        authorDto.bookIds().forEach(bookId -> authorshipService.saveNewAuthorship(newAuthorId, bookId));
        return newAuthor;
    }

    @Override
    public Author update(int id, AuthorDto authorDto) {
        var existingAuthor = getById(id);
        existingAuthor.setName(authorDto.name());
        existingAuthor.setSurname(authorDto.surname());
        var booksForExistingAuthor = authorshipService.getBookIdsForAuthorId(id);
        authorDto.bookIds().stream()  // add new books
                .filter(bookId -> !booksForExistingAuthor.contains(bookId))
                .forEach(bookId -> authorshipService.saveNewAuthorship(id, bookId));
        booksForExistingAuthor.stream()  // delete old books
                .filter(bookId -> !authorDto.bookIds().contains(bookId))
                .forEach(bookId -> authorshipService.deleteAuthorship(id, bookId));
        return existingAuthor;
    }

    @Override
    public void delete(int id) {
        var existingAuthor = getById(id);
        authorshipService.getBookIdsForAuthorId(id).forEach(
                bookId -> authorshipService.deleteAuthorship(id, bookId)
        );
        authorRepo.remove(existingAuthor);
    }

    private int nextId() {
        return authorRepo.stream()
                .map(Author::getId)
                .max(Comparator.comparingInt(id -> id))
                .orElse(0) + 1;
    }
}
