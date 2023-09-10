package com.example.pr3_maven.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "car")
public class Car {
    @Id
    private long id;

    @NotBlank(message = "Марка не должна быть пустой")
    @Size(max = 255, message = "Марка не должна превышать 255 символов")
    private String make;

    @NotBlank(message = "Модель не должна быть пустой")
    @Size(max = 255, message = "Модель не должна превышать 255 символов")
    private String model;

    @NotNull(message = "Год выпуска должен быть указан")
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Год выпуска должен быть в формате YYYY")
    private String year;

    public Car() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
