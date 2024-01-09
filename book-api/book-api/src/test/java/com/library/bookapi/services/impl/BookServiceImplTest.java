package com.library.bookapi.services.impl;

import com.library.bookapi.TestData;
import com.library.bookapi.domain.Book;
import com.library.bookapi.domain.BookEntity;
import com.library.bookapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved() {
        final Book book = TestData.testBook();

        final BookEntity bookEntity = TestData.testBookEntity();

        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result = underTest.save(book);

        assertEquals(book, result);
    }

    @Test
    public void testThatFindByIdReturnEntityWhenNoBook() {
        final String isbn = "123";
        when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());

        final Optional<Book> result = underTest.findById(isbn);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testThatFindByIdReturnBookWhenExists() {
        final Book book = TestData.testBook();
        final BookEntity bookEntity = TestData.testBookEntity();
        when(bookRepository.findById(eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));

        final Optional<Book> result = underTest.findById(book.getIsbn());
        assertEquals(book, result.get());
    }

    @Test
    public void testThatListBooksWhenExists() {
        final Book book = TestData.testBook();
        final BookEntity bookEntity = TestData.testBookEntity();
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));

        final List<Book> result = underTest.listBooks();
        assertEquals(List.of(book), result);
    }

    @Test
    public void testBookNotExistsReturnFalse() {
        final Book book = TestData.testBook();
        when(bookRepository.existsById(any())).thenReturn(false);

        final boolean result = underTest.isBookExists(book);
        assertEquals(false, result);
    }

    @Test
    public void testBookExistsReturnTrue() {
        final Book book = TestData.testBook();
        when(bookRepository.existsById(any())).thenReturn(true);

        final boolean result = underTest.isBookExists(book);
        assertEquals(true, result);
    }

    @Test
    public void testDeleteBook() {
        final String isbn = "123";
        underTest.deleteBookById(isbn);

        verify(bookRepository, times(1)).deleteById(eq(isbn));
    }

}
