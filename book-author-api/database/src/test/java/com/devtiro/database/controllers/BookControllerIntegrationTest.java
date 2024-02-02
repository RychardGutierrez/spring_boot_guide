package com.devtiro.database.controllers;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON).content(createBookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()));
    }

    @Test
    public void testThatListBooksReturnHttpStatus200() throws Exception {
        bookService.save(
                TestDataUtil.createTestBookDtoA(null).getIsbn()
                , TestDataUtil.createTestBookEntityA(null));

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").isString());
    }

    @Test
    public void testThatGetOneBookByIdReturnHttpStatus200() throws Exception {

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);

        bookService.save(bookDto.getIsbn()
                , TestDataUtil.createTestBookEntityA(null));

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").isString());
    }

    @Test
    public void testThatUpdateAllToOneBookByIdReturnHttpStatus200() throws Exception {
        BookDto bookTest = TestDataUtil.createTestBookDtoA(null);
        BookEntity bookEntityTest = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntityTest.getIsbn(), bookEntityTest);

        // Update the author
        bookTest.setTitle("Update name");

        String bookJson = objectMapper.writeValueAsString(bookTest);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookTest.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookTest.getTitle()));
    }

    @Test
    public void testThatPartialUpdateToOneBookByIdReturnHttpStatus200() throws Exception {
        BookDto bookTest = TestDataUtil.createTestBookDtoA(null);
        BookEntity bookEntityTest = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntityTest.getIsbn(), bookEntityTest);

        // Update the author
        bookTest.setTitle("Update name");

        String bookJson = objectMapper.writeValueAsString(bookTest);

        mockMvc.perform(MockMvcRequestBuilders.patch("/books/" + bookTest.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookTest.getTitle()));
    }
}
