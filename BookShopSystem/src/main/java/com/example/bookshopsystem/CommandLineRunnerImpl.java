package com.example.bookshopsystem;

import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.entities.EditionType;
import com.example.bookshopsystem.service.AuthorService;
import com.example.bookshopsystem.service.BookService;
import com.example.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
        //Scanner scanner = new Scanner(System.in);
        //seedData();
        //printAllByReleaseDateNot(LocalDate.of(2000,1,1));
        //printAllByReleaseDateBefore(LocalDate.of(1992,4,12));
        //printAllByTitleContaining("sK");
        //printAllByAuthorLastNameStartsWith("gr");
        //printAllCountTitleLongerThan(12);
        //printAllCountCopiesOfBookByAuthor("Randy","Graham");
        //printByTitleBookInfo("Things Fall Apart");

    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

//    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
//        bookService
//                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
//                .forEach(System.out::println);
//    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }


   private void printAllBooksByAgeRestriction(String ageRestriction){
        bookService.findAllByAgeRestriction(ageRestriction);
   }

   private void printAllWithEditionTypeAndCopiesLessThan(EditionType editionType,int copies){
        bookService.findAllByEditionTypeAndCopiesLessThan(editionType,copies);
   }

   private void printAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher){
        bookService.findAllByPriceIsLessThanOrPriceGreaterThan(lower,higher);
   }

   private void printAllByReleaseDateNot(LocalDate date){
        bookService.findAllByReleaseDateNot(date);
   }

   private void printAllByReleaseDateBefore(LocalDate date){
        bookService.findAllByReleaseDateBefore(date);
   }

   private void printAllByFirstNameEndingWith(String endsWith){
        authorService.findAllByFirstNameEndingWith(endsWith);
   }

   private void printAllByTitleContaining(String content){
        bookService.findAllByTitleContaining(content);
   }

   private void printAllByAuthorLastNameStartsWith(String content){
        bookService.findAllByAuthorLastNameStartsWith(content);
   }

   private void printAllCountTitleLongerThan(Integer length){
        bookService.findAllCountTitleLongerThan(length);
   }

   private void printAllCountCopiesOfBookByAuthor(String authorFirstName,String authorLastName){
        bookService.findAllCountCopiesOfBookByAuthor(authorFirstName,authorLastName);
   }

   private void printByTitleBookInfo(String bookTitle){
       bookService.findByTitle(bookTitle);
   }


}
