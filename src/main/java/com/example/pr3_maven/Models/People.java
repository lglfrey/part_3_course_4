package com.example.pr3_maven.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "people")
public class People {
    @Id
    private long id;

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(max = 255, message = "Имя не должно превышать 255 символов")
    private String firstName;

    @NotBlank(message = "Фамилия не должна быть пустой")
    @Size(max = 255, message = "Фамилия не должна превышать 255 символов")
    private String lastName;

    @Size(max = 255, message = "Отчество не должно превышать 255 символов")
    private String middleName;

    @NotNull(message = "Возраст должен быть указан")
    private int age;

    @NotBlank(message = "Пол не должен быть пустым")
    @Size(max = 10, message = "Пол не должен превышать 10 символов")
    private String gender;

    @Size(max = 255, message = "Род деятельности не должен превышать 255 символов")
    private String occupation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public People() {
    }

}
