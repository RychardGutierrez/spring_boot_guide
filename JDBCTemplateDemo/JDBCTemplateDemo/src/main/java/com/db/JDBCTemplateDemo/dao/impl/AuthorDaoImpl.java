package com.db.JDBCTemplateDemo.dao.impl;

import com.db.JDBCTemplateDemo.dao.AuthorDao;
import com.db.JDBCTemplateDemo.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;


    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update("INSERT INTO authors (id, name, age) VALUES (?,?,?)",
                author.getId(), author.getName(), author.getAge());
    }

    @Override
    public Optional<Author> findOne(long l) {
        List<Author> authors = jdbcTemplate.query(
                "SELECT id, name, age FROM authors WHERE id=? LIMIT 1",
                new AuthorMapper(),
                l);

        return authors.stream().findFirst();
    }

    @Override
    public List<Author> find() {
        List<Author> authors = jdbcTemplate.query("SELECT id, name, age FROM authors", new AuthorMapper());
        return authors;
    }

    @Override
    public void update(Long id, Author author) {
        jdbcTemplate
                .update(
                        "UPDATE authors SET id=?, name=?, age=? WHERE id = ?"
                        , author.getId()
                        , author.getName()
                        , author.getAge()
                        , id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM authors where id =?", id);
    }

    public static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return Author.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .age(resultSet.getInt("age"))
                    .build();
        }
    }
}
