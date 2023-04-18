package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/users")
@Validated
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ArrayList<User> getUsers() {
        log.debug("поулчен запрос GET /users");
        return userService.getUserStorage().getUsers();
    }

    @GetMapping("/{userId}")
    public User getUsersById(@PathVariable(required = false) Long userId) {
        log.debug("поулчен запрос GET /users/id");
        return userService.getUserStorage().getUser(userId);

    }

    @GetMapping("/{userId}/friends")
    public ArrayList<User> getUsersFriends(@PathVariable Long userId) {
        log.debug("поулчен запрос GET /users/id/friends");
        return userService.getUserStorage().getUserList(userService.getUserStorage().getUser(userId).getFriends());
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public ArrayList<User> getUsersCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {
        log.debug("поулчен запрос GET /users/id/friends/common/otherId");
        return userService.getUserStorage().getUserList(userService.commonFriends(userService.getUserStorage().getUser(userId), userService.getUserStorage().getUser(otherId)));
    }

    @PostMapping()
    User create(@RequestBody @Valid User user) {
        log.info("поулчен запрос POST /users");
        userService.getUserStorage().save(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        log.debug("поулчен запрос PUT /users");
        userService.getUserStorage().update(user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("поулчен запрос PUT /users/id/friends/friendId");
        userService.addFriend(userService.getUserStorage().getUser(id), userService.getUserStorage().getUser(friendId));
        return userService.getUserStorage().getUser(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("поулчен запрос DELETE /users/id/friends/friendId");
        userService.removeFriend(userService.getUserStorage().getUser(id), userService.getUserStorage().getUser(friendId));
        return userService.getUserStorage().getUser(id);
    }
}
