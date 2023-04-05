package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
@Validated
public class UserController {
    UserRepository userRepository = new UserRepository();


    @GetMapping
    public Object[] getUsers() {
        log.debug("поулчен запрос GET /users");
        return userRepository.getUserRepository().values().toArray();
    }

    @PostMapping()
    User create(@RequestBody @Valid User user) {
        log.info("поулчен запрос POST /users");
        userRepository.save(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        log.debug("поулчен запрос PUT /users");
        userRepository.update(user);
        return user;
    }
}
