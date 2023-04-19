package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findUserById(String id);
}
