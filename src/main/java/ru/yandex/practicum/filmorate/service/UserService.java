package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserService {


    public void addFriend(User user1, User user2) {
        user1.getFriends().add(user2.getId());
        user2.getFriends().add(user1.getId());
    }

    public void removeFriend(User user1, User user2) {
        user1.getFriends().remove(user2.getId());
        user2.getFriends().remove(user1.getId());
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