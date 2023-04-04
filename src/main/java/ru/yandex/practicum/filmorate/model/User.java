package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@Builder
public class User {

    Long id;
    @Email
    String email;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$")
    String login;
    @Max(20)
    String name;
    @Past
    LocalDate birthday;

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        if (name == null) {
            this.name = login;
        } else {
            this.name = name;
        }

        this.birthday = birthday;
    }
}
