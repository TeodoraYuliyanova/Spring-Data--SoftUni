package com.example.bookshopsystem.service;

import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.entities.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

   // List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(String ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType,int copies);

    List<Book> findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal lowerPrice, BigDecimal higherPrice);

    List<Book> findAllByReleaseDateNot(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContaining(String content);

    List<Book> findAllByAuthorLastNameStartsWith(String content);

    Integer findAllCountTitleLongerThan(Integer length);

    Integer findAllCountCopiesOfBookByAuthor(String authorFirstName, String authorLastName);

    String findByTitle(String bookTitle);

}
