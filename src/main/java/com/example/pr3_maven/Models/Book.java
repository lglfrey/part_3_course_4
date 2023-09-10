package com.example.pr3_maven.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Заголовок не должен быть пустым")
    @Size(max = 255, message = "Заголовок не должен превышать 255 символов")
    private String title;

    @NotBlank(message = "Автор не должен быть пустым")
    @Size(max = 100, message = "Автор не должен превышать 100 символов")
    private String author;

    @NotNull(message = "Год издания должен быть указан")
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Год издания должен быть в формате YYYY")
    private String year;

    public Book() {
    }

    public long getId() {
        return id;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
