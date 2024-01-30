package com.db.JDBCTemplateDemo.dao.impl;

import com.db.JDBCTemplateDemo.dao.BookDao;
import com.db.JDBCTemplateDemo.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO books (isbn, title, author_id) VALUES (?,?,?)",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId());
    }

    @Override
    public Optional<Book> findOne(String number) {
        List<Book> books = jdbcTemplate.query("SELECT isbn, title, author_id from books WHERE isbn=? LIMIT 1",
                new BookMapper(),
                number);
        return books.stream().findFirst();
    }

    @Override
    public List<Book> find() {
        return jdbcTemplate
                .query("SELECT isbn, title, author_id from books"
                        , new BookMapper());
    }

    @Override
    public void update(String isbn, Book book) {
        jdbcTemplate
                .update("UPDATE books SET isbn=?, title=?, author_id=? WHERE isbn = ?",
                        book.getIsbn(), book.getTitle(), book.getAuthorId(), isbn);
    }

    @Override
    public void delete(String isbn) {
        jdbcTemplate.update("DELETE FROM books WHERE isbn = ?", isbn);
    }

    public static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return Book.builder()
                    .isbn(resultSet.getString("isbn"))
                    .title(resultSet.getString("title"))
                    .authorId(resultSet.getLong("author_id"))
                    .build();
        }
    }
}
