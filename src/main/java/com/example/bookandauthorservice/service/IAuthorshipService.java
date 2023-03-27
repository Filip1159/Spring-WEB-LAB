package com.example.bookandauthorservice.service;

import java.util.List;

public interface IAuthorshipService {
    List<Integer> getBookIdsForAuthorId(int authorId);

    List<Integer> getAuthorIdsForBookId(int bookId);

    void saveNewAuthorship(int authorId, int bookId);

    void deleteAuthorship(int authorId, int bookId);
}
