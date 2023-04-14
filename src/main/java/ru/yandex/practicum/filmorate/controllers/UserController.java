package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
@Validated
public class UserController {
    UserStorage userStorage;
    UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }


    @GetMapping
    public Object[] getUsers() {
        log.debug("поулчен запрос GET /users");
        return userStorage.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUsersById(@PathVariable(required = false) Long userId) {
        log.debug("поулчен запрос GET /users/id");
        return userStorage.getUser(userId);

    }

    @GetMapping("/{userId}/friends")
    public ArrayList<User> getUsersFriends(@PathVariable Long userId) {
        log.debug("поулчен запрос GET /users/id/friends");
        return userStorage.getUserList(userStorage.getUser(userId).getFriends());
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public ArrayList<User> getUsersCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {
        log.debug("поулчен запрос GET /users/id/friends/common/otherId");
        return userStorage.getUserList(userService.commonFriends(userStorage.getUser(userId), userStorage.getUser(otherId)));
    }

    @PostMapping()
    User create(@RequestBody @Valid User user) {
        log.info("поулчен запрос POST /users");
        userStorage.save(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        log.debug("поулчен запрос PUT /users");
        userStorage.update(user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("поулчен запрос PUT /users/id/friends/friendId");
        userService.addFriend(userStorage.getUser(id), userStorage.getUser(friendId));
        return userStorage.getUser(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("поулчен запрос DELETE /users/id/friends/friendId");
        userService.removeFriend(userStorage.getUser(id), userStorage.getUser(friendId));
        return userStorage.getUser(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFound(final ValidationException e) {
        return Map.of("error", "Такого юзера нет");
    }

}
