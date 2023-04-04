package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.FilmValidation;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
public class Film {

    private long id;
    @NotBlank
    private String name;

    @Size(max = 200, message = "максимальная длина описания - 200 символов")
    private String description;

    @Past
    @FilmValidation()
    private LocalDate releaseDate;

    @NotNull
    @Min(0)
    private int duration;
}
