package com.hristoskova.springintrohomework.repositories;

import com.hristoskova.springintrohomework.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);
    List<Book> findAllByAuthorFirstNameAndAndAuthorLastName(String firstName, String lastName);
}
