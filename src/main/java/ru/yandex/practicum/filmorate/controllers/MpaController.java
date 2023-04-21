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
import ru.yandex.practicum.filmorate.service.MpaDbService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/mpa")
@Validated
public class MpaController {
    MpaDbService mpaDbService;

    @Autowired
    public MpaController(@Qualifier("mpaDbService") MpaDbService mpaDbService) {
        this.mpaDbService = mpaDbService;
    }


    @GetMapping
    public List<Mpa> getMpa() {
        log.info("поулчен запрос GET /mpa");
        return mpaDbService.getMpa();
    }

    @GetMapping("/{mpaId}")
    public Mpa getMpaById(@PathVariable(required = false) Long mpaId) {
        log.info("поулчен запрос GET /mpa/id");
        return mpaDbService.getMpaById(mpaId);

    }
}
