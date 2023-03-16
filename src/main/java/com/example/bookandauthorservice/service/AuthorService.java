package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.model.Author;
import com.example.bookandauthorservice.model.AuthorDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AuthorService implements IAuthorService {
    private static final List<Author> authorRepo = new ArrayList<>();

    static {
        authorRepo.add(new Author(1, "Adam", "Mickiweicz"));
        authorRepo.add(new Author(2, "Stanisław", "Reymont"));
        authorRepo.add(new Author(3, "Henryk", "Sienkiweicz"));
    }

    @Override
    public Author getById(int authorId) {
        return authorRepo.stream()
                .filter(author -> author.getId() == authorId)
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Author> getAll() {
        return authorRepo;
    }

    @Override
    public Author create(AuthorDto authorDto) {
        var newAuthor = new Author(nextId(), authorDto.name(), authorDto.surname());
        authorRepo.add(newAuthor);
        return newAuthor;
    }

    @Override
    public Author update(int id, AuthorDto authorDto) {
        var existingAuthor = getById(id);
        existingAuthor.setName(authorDto.name());
        existingAuthor.setSurname(authorDto.surname());
        return existingAuthor;
    }

    @Override
    public void delete(int id) {
        var existingAuthor = getById(id);
        authorRepo.remove(existingAuthor);
    }

    private int nextId() {
        return authorRepo.stream()
                .map(Author::getId)
                .max(Comparator.comparingInt(id -> id))
                .orElse(0) + 1;
    }
}
