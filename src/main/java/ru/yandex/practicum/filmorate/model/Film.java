package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.FilmValidation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class Film {

    Set<Long> likes = new HashSet<>();
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

    private Set<String> genres;
    private FilmRating rating;


}
