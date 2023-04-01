package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;


@Data
public class User {
    @NotNull
    @Min(0)
    private long id;
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;


}
