package com.gcsdevs.livros.services;

import com.gcsdevs.livros.entities.Book;
import com.gcsdevs.livros.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook_Success() {
        Book book = new Book(null, "New Book", "Author", null, "Publisher");
        when(bookRepository.findByTitle("New Book")).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Optional<Book> createdBook = bookService.createBook(book);

        assertTrue(createdBook.isPresent());
        assertEquals("New Book", createdBook.get().getTitle());
    }

    @Test
    void testCreateBook_AlreadyExists() {
        Book book = new Book(null, "Existing Book", "Author", null, "Publisher");
        when(bookRepository.findByTitle("Existing Book")).thenReturn(Optional.of(book));

        Optional<Book> createdBook = bookService.createBook(book);

        assertFalse(createdBook.isPresent());
    }

    @Test
    void testUpdateBook_Success() {
        Book existingBook = new Book(1L, "Old Title", "Old Author", null, "Old Publisher");
        Book updatedBook = new Book(1L, "Updated Title", "Updated Author", null, "Updated Publisher");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Optional<Book> result = bookService.updateBook(1L, updatedBook);

        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
    }

    @Test
    void testUpdateBook_NotFound() {
        Book updatedBook = new Book(1L, "Updated Title", "Updated Author", null, "Updated Publisher");

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.updateBook(1L, updatedBook);

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteBook_Success() {
        Book book = new Book(1L, "Title", "Author", null, "Publisher");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean deleted = bookService.deleteBook(1L);

        assertTrue(deleted);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        boolean deleted = bookService.deleteBook(1L);

        assertFalse(deleted);
        verify(bookRepository, never()).deleteById(anyLong());
    }
}
