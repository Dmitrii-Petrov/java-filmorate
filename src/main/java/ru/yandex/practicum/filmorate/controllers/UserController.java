package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserDbService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@Validated
public class UserController {

    UserService userService;

    @Autowired
    public UserController(@Qualifier("userDbService")UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User> getUsers() {
        log.debug("поулчен запрос GET /users");
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUsersById(@PathVariable(required = false) Long userId) {
        log.debug("поулчен запрос GET /users/id");
        return userService.getUsersById(userId);

    }

    @GetMapping("/{userId}/friends")
    public List<User> getUsersFriends(@PathVariable Long userId) {
        log.debug("поулчен запрос GET /users/id/friends");
        return userService.getUsersFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> getUsersCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {
        log.debug("поулчен запрос GET /users/id/friends/common/otherId");
        return userService.getUsersCommonFriends(userId, otherId);
    }

    @PostMapping()
    public User create(@RequestBody @Valid User user) {
        log.info("поулчен запрос POST /users");
        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        log.debug("поулчен запрос PUT /users");
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("поулчен запрос PUT /users/id/friends/friendId");
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("поулчен запрос DELETE /users/id/friends/friendId");
        return userService.removeFriend(id, friendId);
    }
}
