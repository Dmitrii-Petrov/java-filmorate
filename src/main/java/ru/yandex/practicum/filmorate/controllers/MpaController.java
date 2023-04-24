package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/mpa")
@Validated
public class MpaController {
    MpaService mpaService;

    @Autowired
    public MpaController(@Qualifier("mpaService") MpaService mpaService) {
        this.mpaService = mpaService;
    }


    @GetMapping
    public List<Mpa> getMpa() {
        log.info("поулчен запрос GET /mpa");
        return mpaService.getMpa();
    }

    @GetMapping("/{mpaId}")
    public Mpa getMpaById(@PathVariable(required = false) Integer mpaId) {
        log.info("поулчен запрос GET /mpa/id");
        return mpaService.getMpaById(mpaId);

    }
}
