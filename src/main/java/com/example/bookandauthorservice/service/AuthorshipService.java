package com.example.bookandauthorservice.service;

import com.example.bookandauthorservice.exception.AuthorshipAlreadyExistsException;
import com.example.bookandauthorservice.model.Authorship;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorshipService implements IAuthorshipService {
    private static final List<Authorship> authorshipRepo = new ArrayList<>();

    static {
        authorshipRepo.add(new Authorship(1, 3));
        authorshipRepo.add(new Authorship(2, 2));
        authorshipRepo.add(new Authorship(3, 1));
        authorshipRepo.add(new Authorship(9, 14));
        authorshipRepo.add(new Authorship(10, 14));
        authorshipRepo.add(new Authorship(1, 5));
        authorshipRepo.add(new Authorship(3, 6));
        authorshipRepo.add(new Authorship(4, 7));
        authorshipRepo.add(new Authorship(4, 8));
        authorshipRepo.add(new Authorship(3, 9));
        authorshipRepo.add(new Authorship(3, 10));
        authorshipRepo.add(new Authorship(6, 11));
        authorshipRepo.add(new Authorship(7, 12));
        authorshipRepo.add(new Authorship(8, 13));
    }

    @Override
    public List<Integer> getBookIdsForAuthorId(int authorId) {
        return authorshipRepo.stream()
                .filter(authorship -> authorship.getAuthorId() == authorId)
                .map(Authorship::getBookId)
                .toList();
    }

    @Override
    public List<Integer> getAuthorIdsForBookId(int bookId) {
        return authorshipRepo.stream()
                .filter(authorship -> authorship.getBookId() == bookId)
                .map(Authorship::getAuthorId)
                .toList();
    }

    @Override
    public void saveNewAuthorship(int authorId, int bookId) {
        var newAuthorship = new Authorship(authorId, bookId);
        if (authorshipRepo.contains(newAuthorship))
            throw new AuthorshipAlreadyExistsException(
                    "Author with id: [" + authorId + "] already is an author of book with id: [" + bookId + "]");
        authorshipRepo.add(newAuthorship);
    }

    @Override
    public void deleteAuthorship(int authorId, int bookId) {
        var authorship = new Authorship(authorId, bookId);
        authorshipRepo.remove(authorship);
    }
}
