package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/films")
@Validated
public class FilmController {
    FilmRepository filmRepository = new FilmRepository();


    @GetMapping
    public Object[] getFilms() {
        log.debug("поулчен запрос GET /films");
        return filmRepository.getFilmRepository().values().toArray();
    }

    @PostMapping()
    Film create(@RequestBody @Valid Film film) {
        log.info("поулчен запрос POST /films");
        filmRepository.save(film);
        return film;
    }

    @PutMapping
    Film update(@RequestBody @Valid Film film) {
        log.debug("поулчен запрос PUT /films");
        filmRepository.update(film);
        return film;
    }
}
