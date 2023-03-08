package com.example.bookshopsystem.repository;

import com.example.bookshopsystem.entities.AgeRestriction;
import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    //List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    Optional<List<Book>> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Optional<List<Book>> findAllByEditionTypeAndCopiesLessThan(EditionType editionType,int copies);

    Optional<List<Book>> findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal lowerPrice, BigDecimal higherPrice);

    Optional<List<Book>> findAllByReleaseDateNot(LocalDate date);

    Optional<List<Book>> findAllByReleaseDateBefore(LocalDate date);

    Optional<List<Book>> findAllByTitleContaining(String content);

    Optional<List<Book>> findAllByAuthorLastNameStartsWith(String content);

    @Query("SELECT COUNT(b) FROM Book AS b WHERE LENGTH(b.title) > :length")
    Optional<Integer> findAllCountTitleLongerThan(int length);

    @Query("SELECT SUM(b.copies) AS copies FROM Book b WHERE b.author.firstName = :authorFirstName AND b.author.lastName = :authorLastName ORDER BY copies DESC")
    Optional<Integer> findAllCountCopiesOfBookByAuthor(String authorFirstName, String authorLastName);


    Optional<Book> findByTitle(String bookTitle);

}
