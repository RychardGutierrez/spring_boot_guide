package com.library.bookapi.services;

import com.library.bookapi.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    public Book save(Book book);

    public Optional<Book> findById(String isbn);

    public List<Book> listBooks();

    public boolean isBookExists(Book book);

    public Book update(Book book);

    public void deleteBookById(String isbn);
}
