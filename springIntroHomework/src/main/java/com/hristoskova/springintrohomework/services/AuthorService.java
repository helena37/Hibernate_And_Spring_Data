package com.hristoskova.springintrohomework.services;

import com.hristoskova.springintrohomework.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;
    long getAllAuthorsCount();
    Author findAuthorById(long id);
    List<Author> findAllAuthorsByCountOfBooks();
    List<Author> findAllAuthorsWithBooksBefore1990();
}
