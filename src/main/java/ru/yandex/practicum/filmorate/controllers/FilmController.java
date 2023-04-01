package ru.yandex.practicum.filmorate.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/films")

public class FilmController {
    private final HashMap<Long, Film> filmRepository = new HashMap<>();


    @GetMapping
    public HashMap<Long, Film> getFilms() {
        log.debug("поулчен запрос GET /films");
        return filmRepository;
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        log.debug("поулчен запрос POST /film");
        filmRepository.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        log.debug("поулчен запрос PUT /film");
        filmRepository.put(film.getId(), film);
        return film;
    }
}
