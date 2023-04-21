package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.FilmValidation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;


@Data
@Builder
public class Film {


    Set<Long> likes;
    List<Genre> genres;
    Mpa mpa = new Mpa();
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


    public Film(long id, String name, String description, LocalDate releaseDate, int duration, Set<Long> likes, List<Genre> genres, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = Objects.requireNonNullElseGet(likes, HashSet::new);
        this.genres = Objects.requireNonNullElseGet(genres, ArrayList::new);
        this.mpa = Objects.requireNonNullElseGet(mpa, Mpa::new);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("NAME", name);
        values.put("DESCRIPTION", description);
        values.put("RELEASE_DATE", releaseDate);
        values.put("DURATION", duration);
        values.put("RATING", mpa.getId());
        return values;
    }

}
