package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/users")

public class UserController {
    private final HashMap<Long, User> userRepository = new HashMap<>();


    @GetMapping
    public HashMap<Long, User> getFilms() {
        log.debug("поулчен запрос GET /users");
        return userRepository;
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        log.debug("поулчен запрос POST /user");
        userRepository.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        log.debug("поулчен запрос PUT /user");
        userRepository.put(user.getId(), user);
        return user;
    }
}
