package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

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
        return filmService.getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable(required = false) Long filmId) {
        log.debug("поулчен запрос GET /films/id");
        return filmService.getFilmById(filmId);

    }

    @PostMapping()
    public Film create(@RequestBody @Valid Film film) {
        log.info("поулчен запрос POST /films");
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        log.debug("поулчен запрос PUT /films");
        return filmService.update(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.debug("поулчен запрос PUT /films/{id}/like/{userId}");
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.debug("поулчен запрос DELETE /films/{id}/like/{userId}");
        return filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        log.debug("поулчен запрос get /films/{id}/like/{userId}");
        return filmService.getMostLikedFilms(count);
    }


}
