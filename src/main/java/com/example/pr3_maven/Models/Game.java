package com.example.pr3_maven.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "game")
public class Game {
    @Id
    private long id;
    @NotBlank(message = "Название игры не должно быть пустым")
    @Size(max = 255, message = "Название игры не должно превышать 255 символов")
    private String title;

    @NotBlank(message = "Жанр игры не должен быть пустым")
    @Size(max = 100, message = "Жанр игры не должен превышать 100 символов")
    private String genre;

    @NotNull(message = "Год выпуска игры должен быть указан")
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Год выпуска игры должен быть в формате YYYY")
    private String year;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Game() {
    }

}
