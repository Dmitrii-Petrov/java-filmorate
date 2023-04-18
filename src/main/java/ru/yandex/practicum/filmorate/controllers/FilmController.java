package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
@Validated
public class FilmController {
    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping
    public ArrayList<Film> getFilms() {
        log.debug("поулчен запрос GET /films");
        return filmService.getFilmStorage().getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable(required = false) Long filmId) {
        log.debug("поулчен запрос GET /films/id");
        return filmService.getFilmStorage().getFilm(filmId);

    }

    @PostMapping()
    Film create(@RequestBody @Valid Film film) {
        log.info("поулчен запрос POST /films");
        filmService.getFilmStorage().save(film);
        return film;
    }

    @PutMapping
    Film update(@RequestBody @Valid Film film) {
        log.debug("поулчен запрос PUT /films");
        filmService.getFilmStorage().update(film);
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("поулчен запрос PUT /films/{id}/like/{userId}");
        filmService.addLike(filmService.getFilmStorage().getFilm(id), userId);
        return filmService.getFilmStorage().getFilm(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("поулчен запрос DELETE /films/{id}/like/{userId}");
        filmService.removeLike(filmService.getFilmStorage().getFilm(id), userId);
        return filmService.getFilmStorage().getFilm(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        log.debug("поулчен запрос get /films/{id}/like/{userId}");
        return filmService.getMostLikedFilms(filmService.getFilmStorage(), count);
    }


}
