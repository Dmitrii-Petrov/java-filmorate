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
import ru.yandex.practicum.filmorate.service.GenreDbService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/genres")
@Validated
public class GenreController {
    GenreDbService genreDbService;

    @Autowired
    public GenreController(@Qualifier("genreDbService") GenreDbService genreDbService) {
        this.genreDbService = genreDbService;
    }


    @GetMapping
    public List<Genre> getMpa() {
        log.info("поулчен запрос GET /genre");
        return genreDbService.getGenre();
    }

    @GetMapping("/{genreId}")
    public Genre getMpaById(@PathVariable(required = false) Integer genreId) {
        log.info("поулчен запрос GET /genre/id");
        return genreDbService.getGenreById(genreId);

    }
}
