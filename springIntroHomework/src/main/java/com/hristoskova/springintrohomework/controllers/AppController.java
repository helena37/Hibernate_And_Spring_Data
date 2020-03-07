package com.hristoskova.springintrohomework.controllers;
import com.hristoskova.springintrohomework.entities.Book;
import com.hristoskova.springintrohomework.services.AuthorService;
import com.hristoskova.springintrohomework.services.BookService;
import com.hristoskova.springintrohomework.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Controller
public class AppController implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private BufferedReader reader;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        //seed data
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();

        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please, enter the number of exercise between 1 and 4: ");
        String exerciseNumber;

        while (!"5".equals(exerciseNumber = reader.readLine())) {
            switch (exerciseNumber) {
                case "1":
                    //Ex 1
                    List<Book> books = this.bookService.getAllBooksAfter2000();
                    books.forEach(b -> System.out.println(b.getTitle()));
                    break;
                case "2":
                    //Ex 2
                    this.authorService
                            .findAllAuthorsWithBooksBefore1990()
                            .forEach(author -> System.out.println(author.getFirstName()
                                    + " "
                                    + author.getLastName()));
                    break;
                case "3":
                    //Ex 3
                    this.authorService
                            .findAllAuthorsByCountOfBooks()
                            .forEach(a -> {
                                System.out.println(String.format(
                                        "%s %s %d",
                                        a.getFirstName(),
                                        a.getLastName(),
                                        a.getBooks().size()
                                ));
                            });
                    break;
                case "4":
                    List<Book> bookList = this.bookService.getAllBooksOfAuthorWithGivenName();
                    bookList
                            .stream()
                            .sorted((b1, b2) -> {
                                int sorted = b2.getReleaseDate().compareTo(b1.getReleaseDate());

                                if (sorted == 0) {
                                    sorted = b1.getTitle().compareTo(b2.getTitle());
                                }
                                return sorted;
                            })
                            .forEach(book -> System.out.println(
                            String.format(
                                    "Book title: %s \r\nRelease date: %s \r\nCopies: %d",
                                    book.getTitle(),
                                    book.getReleaseDate(),
                                    book.getCopies()
                            )
                    ));
                    break;
                default:
                    System.out.println("!!!Exercise with this number doesn't exist!!!");
                    break;
            }
            System.out.println("If you want to exit, enter 5");
            System.out.println("Please, enter the number of exercise between 1 and 4: ");
        }
    }
}
