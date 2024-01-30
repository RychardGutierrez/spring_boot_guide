package com.db.JDBCTemplateDemo.dao;

import com.db.JDBCTemplateDemo.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void create(Book book);

    public Optional<Book> findOne(String number);

    List<Book> find();

    void update(String isbn, Book book);

    void delete(String isbn);
}
