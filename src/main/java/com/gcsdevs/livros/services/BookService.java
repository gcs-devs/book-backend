package com.gcsdevs.livros.services;

import com.gcsdevs.livros.entities.Book;
import com.gcsdevs.livros.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing {@link Book} entities.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Constructor that initializes the BookService with a BookRepository instance.
     *
     * @param bookRepository the repository for managing books
     */
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves a paginated list of all books.
     *
     * @param page the page number to retrieve (0-based)
     * @param size the number of items per page
     * @return a {@link Page} of books
     */
    public Page<Book> getAllBooks(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size));
    }

    /**
     * Retrieves a book by its unique ID.
     *
     * @param id the ID of the book to retrieve
     * @return an {@link Optional} containing the book, or empty if not found
     */
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Creates a new book if a book with the same title does not already exist.
     *
     * @param book the book to create
     * @return an {@link Optional} containing the created book, or empty if a book with the same title already exists
     */
    public Optional<Book> createBook(Book book) {
        if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(bookRepository.save(book));
    }

    /**
     * Updates an existing book by its ID.
     *
     * @param id   the ID of the book to update
     * @param book the updated book data
     * @return an {@link Optional} containing the updated book, or empty if the book was not found
     */
    public Optional<Book> updateBook(Long id, Book book) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    book.setId(id);
                    return bookRepository.save(book);
                });
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     * @return {@code true} if the book was deleted, or {@code false} if the book was not found
     */
    public boolean deleteBook(Long id) {
        return bookRepository.findById(id).map(book -> {
            bookRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
