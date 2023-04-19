package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Set;

@Data
@Component("userDbStorage")
@Repository
public class UserDbStorage implements UserStorage {

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User getUser(Long id) {
        return null;
    }

    @Override
    public ArrayList<User> getUsers() {
        return null;
    }

    @Override
    public ArrayList<User> getUserList(Set<Long> userIdList) {
        return null;
    }
}
