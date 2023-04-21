package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service("userService")
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
        return getUserStorage().getUserList(getUserStorage().getUser(userId).getFriends());
    }

    public List<User> getUsersCommonFriends(Long userId, Long otherId) {
        return getUserStorage().getUserList(commonFriends(getUserStorage().getUser(userId), getUserStorage().getUser(otherId)));
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
        getUsersById(userId1).getFriends().add(getUsersById(userId2).getId());
        getUsersById(userId2).getFriends().add(getUsersById(userId1).getId());
        return getUsersById(userId1);
    }

    public User removeFriend(Long userId1, Long userId2) {
        getUsersById(userId1).getFriends().remove(getUsersById(userId2).getId());
        getUsersById(userId2).getFriends().remove(getUsersById(userId1).getId());
        return getUsersById(userId1);
    }

    public Set<Long> commonFriends(User user1, User user2) {
        Set<Long> commonFriends = new HashSet<>();
        if (user1.getFriends() != null) {
            commonFriends.addAll(user1.getFriends());
        }

        if (user2.getFriends() != null) {
            commonFriends.retainAll(user2.getFriends());
        }
        return commonFriends;
    }
}
