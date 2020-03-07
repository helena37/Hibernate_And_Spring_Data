package com.hristoskova.springintrohomework.services.impl;

import com.hristoskova.springintrohomework.entities.*;
import com.hristoskova.springintrohomework.repositories.BookRepository;
import com.hristoskova.springintrohomework.services.AuthorService;
import com.hristoskova.springintrohomework.services.BookService;
import com.hristoskova.springintrohomework.services.CategoryService;
import com.hristoskova.springintrohomework.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.hristoskova.springintrohomework.constants.GlobalConstants.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService,
                           CategoryService categoryService, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] fileContent = this.fileUtil.readFileContent(BOOKS_FILE_PATH);
        Arrays.stream(fileContent)
                .forEach(row -> {
                    String[] params = row.split("\\s+");
                    Author author = this.getRandomAuthor();
                    EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate releaseDate = LocalDate.parse(params[1], formatter);

                    int copies = Integer.parseInt(params[2]);
                    BigDecimal price = new BigDecimal(params[3]);
                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];

                    String title = this.getTitle(params);
                    Set<Category> categories = this.setRandomCategories();

                    Book book = new Book(title, editionType, price, copies, releaseDate,
                            ageRestriction, categories, author);

                    this.bookRepository.saveAndFlush(book);
                });
    }

    @Override
    public List<Book> getAllBooksAfter2000() {
        LocalDate releaseDate = LocalDate.of(2000, 12, 31);
        return this.bookRepository.findAllByReleaseDateAfter(releaseDate);
    }

    @Override
    public List<Book> getAllBooksOfAuthorWithGivenName() {
        String authorsFirstName = "George";
        String authorsLastName = "Powell";
        return this.bookRepository.findAllByAuthorFirstNameAndAndAuthorLastName(authorsFirstName, authorsLastName);
    }

    private Set<Category> setRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        int bound = random.nextInt(3) + 1;
        for (int i = 1; i <= bound; i++) {
            categories.add(this.categoryService.getCategoryById(i));
        }

        return categories;
    }

    private String getTitle(String[] params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 5; i < params.length; i++) {
            sb.append(params[i])
                    .append(" ");
        }

        return sb.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt((int) this.authorService.getAllAuthorsCount()) + 1;

        return this.authorService.findAuthorById(randomId);
    }
}
