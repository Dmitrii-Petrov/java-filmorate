package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
@Validated
public class FilmController {
    FilmStorage filmStorage;
    FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }


    @GetMapping
    public ArrayList<Film> getFilms() {
        log.debug("поулчен запрос GET /films");
        return filmStorage.getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable(required = false) Long filmId) {
        log.debug("поулчен запрос GET /films/id");
        return filmStorage.getFilm(filmId);

    }

    @PostMapping()
    Film create(@RequestBody @Valid Film film) {
        log.info("поулчен запрос POST /films");
        filmStorage.save(film);
        return film;
    }

    @PutMapping
    Film update(@RequestBody @Valid Film film) {
        log.debug("поулчен запрос PUT /films");
        filmStorage.update(film);
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("поулчен запрос PUT /films/{id}/like/{userId}");
        filmService.addLike(filmStorage.getFilm(id), userId);
        return filmStorage.getFilm(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("поулчен запрос DELETE /films/{id}/like/{userId}");
        filmService.removeLike(filmStorage.getFilm(id), userId);
        return filmStorage.getFilm(id);
    }

    @GetMapping("/popular")
    public ArrayList<Film> getPopularFilms(@RequestParam(required = false) Long count) {
        log.debug("поулчен запрос get /films/{id}/like/{userId}");
        return filmService.getMostLikedFilms(filmStorage, count);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleFilmNotFound(final ValidationException e) {
        return Map.of("error", "Такого фильма нет");
    }

}
