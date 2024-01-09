package com.library.bookapi.controllers;

import com.library.bookapi.domain.Book;
import com.library.bookapi.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @PutMapping(path = "/book/{isbn}")
    public ResponseEntity<Book> createBook(@PathVariable final String isbn, @RequestBody final Book book) {
        book.setIsbn(isbn);

        final boolean isBookExists = bookService.isBookExists(book);
        final Book saveBook = bookService.save(book);

        if (isBookExists) {
            return new ResponseEntity<>(saveBook, HttpStatus.OK);
        }
        final ResponseEntity<Book> response = new ResponseEntity<>(saveBook, HttpStatus.CREATED);
        return response;
    }

    @GetMapping(path = "/book/{isbn}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String isbn) {
        final Optional<Book> retrieveBook = bookService.findById(isbn);

        return retrieveBook.map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks() {
        return new ResponseEntity<List<Book>>(bookService.listBooks(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/book/{isbn}")
    public ResponseEntity deleteBook(@PathVariable final String isbn) {
        bookService.deleteBookById(isbn);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
