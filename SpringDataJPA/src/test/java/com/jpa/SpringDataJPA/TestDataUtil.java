package com.jpa.SpringDataJPA;

import com.jpa.SpringDataJPA.domain.Author;
import com.jpa.SpringDataJPA.domain.Book;

public class TestDataUtil {
    private TestDataUtil() {

    }

    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("John Test")
                .age(70)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Ana Test")
                .age(40)
                .build();
    }

    public static Book createTestBook(final Author author) {
        return Book.builder()
                .isbn("123")
                .title("Title test")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("124")
                .title("Title test B")
                .author(author)
                .build();
    }
}
