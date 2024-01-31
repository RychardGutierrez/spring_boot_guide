package com.jpa.SpringDataJPA.repository;

import com.jpa.SpringDataJPA.TestDataUtil;
import com.jpa.SpringDataJPA.domain.Author;
import com.jpa.SpringDataJPA.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Iterator;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {

    private final AuthorRepository authorRepository;


    @Autowired
    public AuthorRepositoryIntegrationTest(final AuthorRepository authorDao) {
        this.authorRepository = authorDao;
    }

    @Test
    public void testThatAuthorCanBeCreate() {
        Author author = TestDataUtil.createTestAuthor();


        authorRepository.save(author);
        Optional<Author> result = authorRepository.findById(author.getId());

        assert result.isPresent();
        assert result.get().equals(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthor();
        authorRepository.save(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorB);

        Iterable<Author> result = authorRepository.findAll();
        assertThat(result).hasSize(2);
        assertThat(result).contains(authorA);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor();
        authorRepository.save(author);
        author.setName("UPDATE");
        authorRepository.save(author);

        Optional<Author> result = authorRepository.findById(author.getId());

        assert result.get().getName().equals("UPDATE");
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthor();
        authorRepository.save(author);

        authorRepository.deleteById(author.getId());

        Optional<Author> result = authorRepository.findById(author.getId());

        assert !result.isPresent();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        Author authorA = TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthorB();

        authorRepository.save(authorA);
        authorRepository.save(authorB);

        Iterable<Author> result = authorRepository.ageLessThan(50);
        assertThat(result).hasSize(1);
        assertThat(result).contains(authorB);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        Author authorA = TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthorB();

        authorRepository.save(authorA);
        authorRepository.save(authorB);

        Iterable<Author> result = authorRepository.findAuthorsWithAgeGreaterThan(50);
        assertThat(result).hasSize(1);
        assertThat(result).contains(authorA);
    }
}
