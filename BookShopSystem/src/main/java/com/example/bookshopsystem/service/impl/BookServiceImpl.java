package com.example.bookshopsystem.service.impl;

import com.example.bookshopsystem.entities.*;
import com.example.bookshopsystem.repository.BookRepository;
import com.example.bookshopsystem.service.AuthorService;
import com.example.bookshopsystem.service.BookService;
import com.example.bookshopsystem.service.CategoryService;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

//    @Override
//    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
//        return bookRepository
//                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
//                .stream()
//                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
//                        book.getAuthor().getLastName()))
//                .distinct()
//                .collect(Collectors.toList());
//    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }

    @Override
    public List<Book> findAllByAgeRestriction(String restriction) {
        final AgeRestriction ageRestriction = AgeRestriction.valueOf(restriction.toUpperCase());

        List<Book> books = this.bookRepository.findAllByAgeRestriction(ageRestriction).orElseThrow(NoSuchElementException::new);

        books.stream().map(Book::getTitle).forEach(System.out::println);
        return books;
    }

    @Override
    public List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies) {
        List<Book> books = this.bookRepository.findAllByEditionTypeAndCopiesLessThan(editionType, copies).orElseThrow(NoSuchElementException::new);

        books.stream().map(Book::getTitle).forEach(System.out::println);
        return books;
    }

    public List<Book> findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher) {
        List<Book> books = this.bookRepository.findAllByPriceIsLessThanOrPriceGreaterThan(lower, higher).orElseThrow(NoSuchElementException::new);

        books.stream().map(Book::toString).forEach(System.out::println);
        return books;
    }

    public List<Book> findAllByReleaseDateNot(LocalDate date) {
        List<Book> books = this.bookRepository.findAllByReleaseDateNot(date).orElseThrow(NoSuchElementException::new);

        books.stream().map(Book::getTitle).forEach(System.out::println);
        return books;
    }

    @Override
    public List<Book> findAllByReleaseDateBefore(LocalDate date) {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(date).orElseThrow(NoSuchElementException::new);

        books.stream().map(Book::titleEditionTypeAndPrice).forEach(System.out::println);
        return books;
    }

    @Override
    public List<Book> findAllByTitleContaining(String content) {
        List<Book> books = this.bookRepository.findAllByTitleContaining(content).orElseThrow(NoSuchElementException::new);

        books.stream().map(Book::getTitle).forEach(System.out::println);
        return books;
    }

    @Override
    public List<Book> findAllByAuthorLastNameStartsWith(String content) {
        List<Book> books = this.bookRepository.findAllByAuthorLastNameStartsWith(content).orElseThrow(NoSuchElementException::new);

        books.stream().map(Book::titleAndAuthor).forEach(System.out::println);
        return books;
    }

    @Override
    public Integer findAllCountTitleLongerThan(Integer length) {
        Integer count = this.bookRepository.findAllCountTitleLongerThan(length).orElseThrow(NoSuchElementException::new);
        System.out.println(count);
        return count;
    }

    @Override
    public Integer findAllCountCopiesOfBookByAuthor(String authorFirstName, String authorLastName) {
       Integer bookCopies =  this.bookRepository.findAllCountCopiesOfBookByAuthor(authorFirstName,authorLastName).orElseThrow(NoSuchElementException::new);
        System.out.println(bookCopies);
        return bookCopies;
    }

    @Override
    public String findByTitle(String bookTitle) {
        String bookInfo = this.bookRepository.findByTitle(bookTitle).map(Book::titleEditionTypeAgeRestrictionAndPrice).orElseThrow(NoSuchElementException::new);
        System.out.println(bookInfo);
        return bookInfo;
    }


}
