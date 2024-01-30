package com.db.JDBCTemplateDemo;

import com.db.JDBCTemplateDemo.domain.Author;
import com.db.JDBCTemplateDemo.domain.Book;

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
                .age(50)
                .build();
    }

    public static Book createTestBook() {
        return Book.builder()
                .isbn("123")
                .title("Title test")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("124")
                .title("Title test B")
                .authorId(2L)
                .build();
    }
}
