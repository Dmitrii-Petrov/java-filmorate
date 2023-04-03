package ru.yandex.practicum.filmorate.repository;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@Data
public class UserRepository {
    HashMap<Long, User> userRepository = new HashMap<>();
    long generatorId = 0;

    public long generateId() {
        return ++generatorId;
    }

    public void save(User user) {
        user.setId(generateId());
        userRepository.put(user.getId(), user);
    }

    public void update(User user) {
        if (!userRepository.containsKey(user.getId())) {
            throw new RuntimeException("юзер не существует");
        }
        userRepository.put(user.getId(), user);
    }
}
