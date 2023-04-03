package ru.yandex.practicum.filmorate.servise;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateServiceTest {

    @Test
    void validateServiceTest() {
        ValidateService validateService = new ValidateService();
        Film film = new Film();
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(RuntimeException.class, () -> validateService.validateFilm(film));
        film.setReleaseDate(LocalDate.of(1895, 12, 29));
        assertDoesNotThrow(() -> validateService.validateFilm(film));

    }


}