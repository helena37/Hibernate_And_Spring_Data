package com.hristoskova.springintrohomework.repositories;

import com.hristoskova.springintrohomework.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author a order by a.books.size desc")
    List<Author> findAuthorByCountOfBooks();

    @Query("select a from Author a join a.books b where b.releaseDate < '1990-01-01'")
    List<Author> findAuthorsByBooksBefore1990();
}
