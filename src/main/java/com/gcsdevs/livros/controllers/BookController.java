package com.gcsdevs.livros.controllers;

import com.gcsdevs.livros.entities.Book;
import com.gcsdevs.livros.exceptions.BookAlreadyExistsException;
import com.gcsdevs.livros.exceptions.BookNotFoundException;
import com.gcsdevs.livros.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Livros", description = "CRUD de livros")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Listar todos os livros", description = "Retorna todos os livros paginados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping
    public Page<Book> getAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooks(page, size);
    }

    @Operation(summary = "Buscar livro por ID", description = "Retorna um livro específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Operation(summary = "Criar um novo livro", description = "Adiciona um novo livro ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado"),
            @ApiResponse(responseCode = "409", description = "Livro já existe")
    })
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        if (bookService.createBook(book).isPresent()) {
            throw new BookAlreadyExistsException("Book already exists with title: " + book.getTitle());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @Operation(summary = "Atualizar um livro", description = "Atualiza as informações de um livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.updateBook(id, book)
                .map(updatedBook -> ResponseEntity.ok(updatedBook))
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Operation(summary = "Deletar um livro", description = "Remove um livro do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro deletado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (!deleted) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
