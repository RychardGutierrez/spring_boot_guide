package com.library.bookapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.bookapi.TestData;
import com.library.bookapi.domain.Book;
import com.library.bookapi.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testThatBookIsCreatedReturn200() throws Exception {
        final Book book = TestData.testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/book/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor())
                );
    }

    @Test
    public void testThatBookIsUpdatedReturn201() throws Exception {
        final Book book = TestData.testBook();
        bookService.save(book);

        book.setAuthor("new author");
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/book/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor())
                );
    }

    @Test
    public void testThatRetrieveBookReturn404WhenBookNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/123"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveBookReturnOkWhenBookIsFound() throws Exception {
        final Book book = TestData.testBook();
        bookService.save(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/" + book.getIsbn())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor())
                );
    }

    @Test
    public void testThatListBooksReturnsHttp200EmptyListWhenNoBooksExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }

    @Test
    public void testThatListBooksReturnsHttp200ListBooksWhenNoBooksExists() throws Exception {
        final Book book = TestData.testBook();
        bookService.save(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[{\"isbn\":\"0123123\",\"author\":\"Virginia Woolf\",\"title\":\"test book\"}]"));

    }

    @Test
    public void testThatHttp204IsReturnWhenBookDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/1232342444"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

