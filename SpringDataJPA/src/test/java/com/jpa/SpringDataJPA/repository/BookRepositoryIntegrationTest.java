package com.jpa.SpringDataJPA.repository;

import com.jpa.SpringDataJPA.TestDataUtil;
import com.jpa.SpringDataJPA.domain.Author;
import com.jpa.SpringDataJPA.domain.Book;
import com.jpa.SpringDataJPA.repositories.AuthorRepository;
import com.jpa.SpringDataJPA.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testThatBookCanBeCreate() {
        Author author = TestDataUtil.createTestAuthor();
        authorRepository.save(author);

        Book book = TestDataUtil.createTestBook(author);
        bookRepository.save(book);


        Optional<Book> result = bookRepository.findById(book.getIsbn());

        assert result.isPresent();
        assert result.get().equals(book);
    }

    @Test
    public void testThatMultipleAuthorCanBeCreateAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();
        authorRepository.save(author);

        Author authorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorB);

        Book bookA = TestDataUtil.createTestBook(author);
        bookRepository.save(bookA);
        Book bookB = TestDataUtil.createTestBookB(authorB);
        bookRepository.save(bookB);

        Iterable<Book> result = bookRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).contains(bookB);
    }

    @Test
    void testThatBookCanBenUpdate() {
        Author author = TestDataUtil.createTestAuthor();
        authorRepository.save(author);

        Book bookA = TestDataUtil.createTestBook(author);
        bookRepository.save(bookA);

        bookA.setTitle("UPDATE");
        bookRepository.save(bookA);

        Optional<Book> result = bookRepository.findById(bookA.getIsbn());
        assert result.get().equals(bookA);
    }

    @Test
    void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthor();
        authorRepository.save(author);

        Book bookA = TestDataUtil.createTestBook(author);
        bookRepository.save(bookA);

        bookRepository.deleteById(bookA.getIsbn());

        Optional<Book> result = bookRepository.findById(bookA.getIsbn());
        assert !result.isPresent();
    }
}
