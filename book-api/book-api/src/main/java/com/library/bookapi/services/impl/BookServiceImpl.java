package com.library.bookapi.services.impl;

import com.library.bookapi.domain.Book;
import com.library.bookapi.domain.BookEntity;
import com.library.bookapi.repository.BookRepository;
import com.library.bookapi.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(final Book book) {
        final BookEntity bookEntity = bookToEntityBook(book);
        final BookEntity savedBook = bookRepository.save(bookEntity);

        return bookEntityToBook(savedBook);
    }

    @Override
    public Optional<Book> findById(String isbn) {
        final Optional<BookEntity> foundBook = bookRepository.findById(isbn);
        return foundBook.map(book -> bookEntityToBook(book));
    }

    @Override
    public List<Book> listBooks() {
        return bookRepository.findAll().stream().map(bookEntity -> bookEntityToBook(bookEntity)).collect(Collectors.toList());
    }

    @Override
    public boolean isBookExists(Book book) {
        return bookRepository.existsById(book.getIsbn());
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public void deleteBookById(String isbn) {
        try {
            bookRepository.deleteById(isbn);
        } catch (final EmptyResultDataAccessException ex) {
            log.debug("Book with isbn {} not found", isbn);
        }

    }

    private BookEntity bookToEntityBook(Book book) {
        return BookEntity.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }

    private Book bookEntityToBook(BookEntity bookEntity) {
        return Book.builder()
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .build();
    }
}
