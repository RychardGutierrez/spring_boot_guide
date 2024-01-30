package com.db.JDBCTemplateDemo.dao;

import com.db.JDBCTemplateDemo.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
   public void create(Author author);

    public Optional<Author> findOne(long l);

    List<Author> find();

    void update(Long id, Author author);

    void delete(Long id);
}
