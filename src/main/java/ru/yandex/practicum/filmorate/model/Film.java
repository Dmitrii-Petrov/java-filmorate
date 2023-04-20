package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.FilmValidation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    private String genres;
    @NotNull
    private String rating;


    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("NAME", name);
        values.put("DESCRIPTION", description);
        values.put("RELEASE_DATE", releaseDate);
        values.put("DURATION", duration);
        values.put("RATING", rating);
        return values;
    }

}
