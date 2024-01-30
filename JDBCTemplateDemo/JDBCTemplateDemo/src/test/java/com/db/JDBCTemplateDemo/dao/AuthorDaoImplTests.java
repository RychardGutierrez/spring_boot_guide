package com.db.JDBCTemplateDemo.dao;

import com.db.JDBCTemplateDemo.TestDataUtil;
import com.db.JDBCTemplateDemo.dao.impl.AuthorDaoImpl;
import com.db.JDBCTemplateDemo.domain.Author;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl authorDao;

    @Test
    public void testCreateAuthorGenerateCorrectSql() {
        Author author = TestDataUtil.createTestAuthor();

        authorDao.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?,?,?)"),
                eq(1L),
                eq("John Test"),
                eq(70)
        );
    }


    @Test
    public void testThatFindOneGeneratesTheCorrectSql() {
        authorDao.findOne(1L);

        verify(jdbcTemplate)
                .query(eq("SELECT id, name, age FROM authors WHERE id=? LIMIT 1"),
                        ArgumentMatchers.<AuthorDaoImpl.AuthorMapper>any(),
                        eq(1l));
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql() {
        authorDao.find();

        verify(jdbcTemplate)
                .query(eq("SELECT id, name, age FROM authors")
                        , ArgumentMatchers.<AuthorDaoImpl.AuthorMapper>any());

    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthor();
        authorDao.update(3L, author);

        verify(jdbcTemplate)
                .update(
                        "UPDATE authors SET id=?, name=?, age=? WHERE id = ?"
                        , 1L, "John Test", 70, 3L);
    }

    @Test
    public void testThatDeleteGeneratesTheCorrectSql() {
        authorDao.delete(1L);
        verify(jdbcTemplate).update("DELETE FROM authors where id =?", 1L);
    }

}
