package com.devtiro.database.controllers;


import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.mappers.Mapper;
import com.devtiro.database.mappers.impl.BookMapper;
import com.devtiro.database.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> bookMapper;

    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PostMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        BookEntity book = bookMapper.mapFrom(bookDto);
        BookEntity bookEntity = bookService.save(isbn, book);
        BookDto saveBook = bookMapper.mapTo(bookEntity);

        return new ResponseEntity<>(saveBook, HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAll();
        return books.stream().map(bookMapper::mapTo).toList();
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> book = bookService.findOne(isbn);
        return book.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bookDto.setIsbn(isbn);

        BookEntity book = bookMapper.mapFrom(bookDto);
        BookEntity bookUpdated = bookService.save(isbn, book);

        return new ResponseEntity<>(bookMapper.mapTo(bookUpdated), HttpStatus.OK);
    }

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdate(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity book = bookMapper.mapFrom(bookDto);
        BookEntity bookUpdated = bookService.partialUpdate(isbn, book);

        return new ResponseEntity<>(bookMapper.mapTo(bookUpdated), HttpStatus.OK);
    }
}
