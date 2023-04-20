package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {

    void save(User user);

    void update(User user);

    boolean delete(Long id);

    User getUser(Long id);

    List<User> getUsers();

    List<User> getUserList(Set<Long> userIdList);

}
