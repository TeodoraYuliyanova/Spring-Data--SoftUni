package com.softuni.bookshop.repositories;

import com.softuni.bookshop.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorById(Long id);

}
