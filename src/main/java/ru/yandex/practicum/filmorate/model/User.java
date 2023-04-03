package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;


@Data
@Builder
public class User {

    Long id;
    @Email
    String email;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]*")
    String login;

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
