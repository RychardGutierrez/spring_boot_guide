package com.deviro.books;

import com.deviro.books.domain.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class JacksonTest {

    @Test
    public void testThatObjectMapperCanCreateJsonFromObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = Book.builder()
                .isbn("1230-123")
                .title("The enigma")
                .author("Aria Montgomery")
                .yearPublished("2005")
                .build();
        String result = objectMapper.writeValueAsString(book);

        assertThat(result).isEqualTo("{\"isbn\":\"1230-123\",\"title\":\"The enigma\",\"author\":\"Aria Montgomery\",\"year\":\"2005\"}");
    }

    @Test
    public void testThatObjectMapperCanCreateJavaObjectFromJson() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();

        String bookJson = "{\"foo\":\"fofo\",\"isbn\":\"1230-123\",\"title\":\"The enigma\",\"author\":\"Aria Montgomery\",\"year\":\"2005\"}";

        Book bookExpected = Book.builder()
                .isbn("1230-123")
                .title("The enigma")
                .author("Aria Montgomery")
                .yearPublished("2005")
                .build();

        Book book = objectMapper.readValue(bookJson, Book.class);

        assertThat(book).isEqualTo(bookExpected);
    }
}
