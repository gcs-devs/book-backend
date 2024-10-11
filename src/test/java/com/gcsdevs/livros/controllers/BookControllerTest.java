package com.gcsdevs.livros.controllers;

import com.gcsdevs.livros.entities.Book;
import com.gcsdevs.livros.exceptions.BookAlreadyExistsException;
import com.gcsdevs.livros.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Não é necessário redefinir o bookService aqui, pois ele já está sendo mockado pela anotação @MockBean
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookById_Success() throws Exception {
        Book book = new Book(1L, "Test Title", "Test Author", "Test Genre", "Test Publisher");
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"));
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found with id: 1"));
    }

    @Test
    public void testCreateBook_Success() throws Exception {
        // Garante que o título do livro seja único para evitar conflito
        String uniqueTitle = "Unique Book Title " + System.currentTimeMillis();

        Book book = new Book();
        book.setTitle(uniqueTitle);
        book.setAuthor("Author");
        book.setPublisher("Publisher");

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\""+uniqueTitle+"\", \"author\":\"Author\", \"publisher\":\"Publisher\"}"))
                .andExpect(status().isCreated())  // Espera status 201 Created
                .andExpect(jsonPath("$.title").value(uniqueTitle))
                .andExpect(jsonPath("$.author").value("Author"))
                .andExpect(jsonPath("$.publisher").value("Publisher"));
    }

    @Test
    void testCreateBook_AlreadyExists() throws Exception {
        when(bookService.createBook(any(Book.class))).thenThrow(new BookAlreadyExistsException("Book already exists with title: Existing Book"));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Existing Book\", \"author\":\"Author\", \"publisher\":\"Publisher\"}"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Book already exists with title: Existing Book"));
    }

    @Test
    void testUpdateBook_Success() throws Exception {
        Book existingBook = new Book(1L, "Old Title", "Old Author", null, "Old Publisher");
        Book updatedBook = new Book(1L, "Updated Title", "Updated Author", null, "Updated Publisher");

        when(bookService.getBookById(1L)).thenReturn(Optional.of(existingBook));
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(Optional.of(updatedBook));

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\", \"author\":\"Updated Author\", \"publisher\":\"Updated Publisher\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void testUpdateBook_NotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\", \"author\":\"Updated Author\", \"publisher\":\"Updated Publisher\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found with id: 1"));
    }

    @Test
    void testDeleteBook_Success() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBook_NotFound() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found with id: 1"));
    }
}
