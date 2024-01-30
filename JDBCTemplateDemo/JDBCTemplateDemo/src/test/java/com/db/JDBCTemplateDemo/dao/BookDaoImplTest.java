package com.db.JDBCTemplateDemo.dao;

import com.db.JDBCTemplateDemo.TestDataUtil;
import com.db.JDBCTemplateDemo.dao.impl.BookDaoImpl;
import com.db.JDBCTemplateDemo.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl bookDao;

    @Test
    public void testCreateBookGenerateCorrectSql() {
        Book book = TestDataUtil.createTestBook();

        bookDao.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?,?,?)"),
                eq("123"),
                eq("Title test"),
                eq(1L)
        );
    }

    @Test
    public void testThatFindOneBookGenerateCorrectSql() {
        bookDao.findOne("123");
        verify(jdbcTemplate)
                .query(eq("SELECT isbn, title, author_id from books WHERE isbn=? LIMIT 1"),
                        ArgumentMatchers.<BookDaoImpl.BookMapper>any(), eq("123"));
    }

    @Test
    public void testThatFindManyGeneratesCorrectSsl() {
        bookDao.find();

        verify(jdbcTemplate)
                .query(eq("SELECT isbn, title, author_id from books"),
                        ArgumentMatchers.<BookDaoImpl.BookMapper>any());

    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBook();
        bookDao.update("123", book);

        verify(jdbcTemplate).update("UPDATE books SET isbn=?, title=?, author_id=? WHERE isbn = ?",
                "123", "Title test", 1L, "123");
    }

    @Test
    public void testThatDeleteGenerateCorrectSql() {
        Book book = TestDataUtil.createTestBook();
        bookDao.delete(book.getIsbn());

        verify(jdbcTemplate).update("DELETE FROM books WHERE isbn = ?", book.getIsbn());
    }
}
