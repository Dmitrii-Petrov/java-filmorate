package ru.yandex.practicum.filmorate.tests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTests {
    private final FilmDbStorage filmDbStorage;
    private static Film film;
    private static Mpa mpa;

    @BeforeAll
    static void beforeAll() {
        FilmDbStorageTests.mpa = new Mpa();
        mpa.setId(1);
        FilmDbStorageTests.film = Film.builder()
                .name("test name")
                .duration(100)
                .name("test name")
                .description("test description")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .mpa(mpa)
                .build();
    }

    @Test
    public void testFindUserById() {
        filmDbStorage.save(film);

        Film testFilm = filmDbStorage.getFilm(film.getId());

        Assertions.assertEquals(testFilm.getId(), film.getId());
        Assertions.assertEquals(testFilm.getReleaseDate(), film.getReleaseDate());
        Assertions.assertEquals(testFilm.getDuration(), film.getDuration());
        Assertions.assertEquals(testFilm.getName(), film.getName());
        Assertions.assertEquals(testFilm.getDescription(), film.getDescription());
    }

    @Test
    public void testUpdateUser() {
        filmDbStorage.save(film);
        film.setName("updated test name");
        filmDbStorage.update(film);

        Film testFilm = filmDbStorage.getFilm(film.getId());

        Assertions.assertEquals(testFilm.getId(), film.getId());
        Assertions.assertEquals(testFilm.getReleaseDate(), film.getReleaseDate());
        Assertions.assertEquals(testFilm.getDuration(), film.getDuration());
        Assertions.assertEquals(testFilm.getName(), film.getName());
        Assertions.assertEquals(testFilm.getDescription(), film.getDescription());
    }

    @Test
    public void testDeleteUser() {
        filmDbStorage.save(film);

        filmDbStorage.delete(film.getId());

        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            filmDbStorage.getFilm(film.getId());
        });
    }
} 