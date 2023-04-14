package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Data
@Component
public class InMemoryUserStorage implements UserStorage {
    HashMap<Long, User> userRepository = new HashMap<>();
    long generatorId = 0;

    public long generateId() {
        return ++generatorId;
    }

    @Override
    public void save(User user) {
        user.setId(generateId());
        userRepository.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        if (!userRepository.containsKey(user.getId())) {
            throw new ValidationException("юзер не существует");
        }
        userRepository.put(user.getId(), user);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.containsKey(id)) {
            throw new ValidationException("юзер не существует");
        }
        userRepository.remove(id);

    }

    @Override
    public User getUser(Long id) {
        if (!userRepository.containsKey(id)) {
            throw new ValidationException("юзер не существует");
        }
        return userRepository.get(id);
    }

    @Override
    public Object[] getUsers() {
        return userRepository.values().toArray();
    }

    @Override
    public ArrayList<User> getUserList(Set<Long> userIdList) {
        ArrayList<User> userList = new ArrayList<>();
        for (Long id : userIdList) {
            userList.add(userRepository.get(id));
        }
        return userList;
    }


}
