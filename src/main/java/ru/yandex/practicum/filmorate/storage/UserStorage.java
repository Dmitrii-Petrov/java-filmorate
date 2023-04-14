package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Set;

public interface UserStorage {

    void save(User user);

    void update(User user);

    void delete(Long id);

    User getUser(Long id);

    Object[] getUsers();

    ArrayList<User> getUserList(Set<Long> userIdList);

}
