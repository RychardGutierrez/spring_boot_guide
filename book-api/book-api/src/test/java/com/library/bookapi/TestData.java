package com.library.bookapi;

import com.library.bookapi.domain.Book;
import com.library.bookapi.domain.BookEntity;

public final class TestData {
    private TestData() {
    }

    public static final Book testBook() {
        return Book.builder()
                .isbn("0123123")
                .author("Virginia Woolf")
                .title("test book")
                .build();
    }

    public static final BookEntity testBookEntity() {
        return BookEntity.builder()
                .isbn("0123123")
                .author("Virginia Woolf")
                .title("test book")
                .build();
    }

}
