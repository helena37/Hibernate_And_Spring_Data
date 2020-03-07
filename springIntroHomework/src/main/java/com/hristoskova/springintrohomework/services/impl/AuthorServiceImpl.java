package com.hristoskova.springintrohomework.services.impl;

import com.hristoskova.springintrohomework.entities.Author;
import com.hristoskova.springintrohomework.repositories.AuthorRepository;
import com.hristoskova.springintrohomework.services.AuthorService;
import com.hristoskova.springintrohomework.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

import static com.hristoskova.springintrohomework.constants.GlobalConstants.*;


@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] fileContent = this.fileUtil.readFileContent(AUTHOR_FILE_PATH);
        for (String file : fileContent) {
            String[] firstLastName = file.split("\\s+");
            Author author = new Author(firstLastName[0], firstLastName[1]);
            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public long getAllAuthorsCount() {
        return this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<Author> findAllAuthorsByCountOfBooks() {
        return this.authorRepository.findAuthorByCountOfBooks();
    }

    @Override
    public List<Author> findAllAuthorsWithBooksBefore1990() {
        return this.authorRepository.findAuthorsByBooksBefore1990();
    }
}
