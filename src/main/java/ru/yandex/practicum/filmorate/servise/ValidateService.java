package ru.yandex.practicum.filmorate.servise;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class ValidateService {

    public void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new RuntimeException("кино еще не изобрели");
        }
    }
}
