package com.gcsdevs.livros.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Schema(description = "Representa um livro no sistema")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do livro", example = "1", required = true)
    private Long id;

    @Schema(description = "Título do livro", example = "O Senhor dos Anéis", required = true)
    private String title;

    @Schema(description = "Gênero literário", example = "Fantasia", required = true)
    private String genre;

    @Schema(description = "Nome do autor do livro", example = "J.R.R. Tolkien", required = true)
    private String author;

    @Schema(description = "Editora responsável pela publicação", example = "HarperCollins", required = true)
    private String publisher;

    @Schema(description = "Ano de publicação do livro", example = "1954", required = false)
    private int year;

    // Getters e setters
    public Long getId() {
        return id;
    }

    public Book() {}

    public Book(Long id, String title, String author, String genre, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
