package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;


@Service("userService")
@Data
public class UserService {

    UserStorage userStorage;


    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public List<User> getUsers() {
        return getUserStorage().getUsers();
    }

    public User getUsersById(Long userId) {
        return getUserStorage().getUser(userId);
    }

    public List<User> getUsersFriends(Long userId) {
        return getUserStorage().getUsersFriends(userId);
    }

    public List<User> getUsersCommonFriends(Long userId, Long otherId) {
        return getUserStorage().getUsersCommonFriends(userId, otherId);
    }

    public User create(User user) {
        getUserStorage().save(user);
        return user;
    }

    public User update(User user) {
        getUserStorage().update(user);
        return user;
    }

    public User addFriend(Long userId1, Long userId2) {
        getUserStorage().addFriend(userId1, userId2);
        return getUsersById(userId1);
    }

    public User removeFriend(Long userId1, Long userId2) {
        getUserStorage().removeFriend(userId1, userId2);
        return getUsersById(userId1);
    }
}
