package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/genres")
@Validated
public class GenreController {
    GenreService genreService;

    @Autowired
    public GenreController(@Qualifier("genreService") GenreService genreService) {
        this.genreService = genreService;
    }


    @GetMapping
    public List<Genre> getGenre() {
        log.info("поулчен запрос GET /genre");
        return genreService.getGenre();
    }

    @GetMapping("/{genreId}")
    public Genre getGenreById(@PathVariable(required = false) Integer genreId) {
        log.info("поулчен запрос GET /genre/id");
        return genreService.getGenreById(genreId);

    }
}
