package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service("userDbService")
public class UserDbService extends UserService {

    UserStorage userStorage;

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public UserDbService(@Qualifier("userDbStorage") UserStorage userStorage, JdbcTemplate jdbcTemplate) {
        super(userStorage);
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<User> getUsersFriends(Long userId) {

        String sqlQuery = "select FRIEND_ID from FRIENDS where USER_ID = ?";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId);

    }

    @Override
    public List<User> getUsersCommonFriends(Long userId, Long otherId) {
        List<User> commonFriends = new ArrayList<>();
        if (getUsersFriends(userId) != null) {
            commonFriends.addAll(getUsersFriends(userId));
        }

        if (getUsersFriends(otherId) != null) {
            commonFriends.retainAll(getUsersFriends(otherId));
        }

        return commonFriends;
    }

    @Override
    public User create(User user) {
        getUserStorage().save(user);
        return user;
    }

    @Override
    public User addFriend(Long userId1, Long userId2) {
        if (userId2 > 0) {
            String sqlQuery = "insert into FRIENDS(USER_ID, FRIEND_ID) " +
                    "values (?, ?)";

            jdbcTemplate.update(sqlQuery,
                    userId1,
                    userId2);
        } else throw new UserNotFoundException();
        return getUsersById(userId1);
    }

    @Override
    public User removeFriend(Long userId1, Long userId2) {
        if (userId2 > 0) {
            String sqlQuery = "delete from FRIENDS where ((USER_ID = ?) and (FRIEND_ID = ?))";
            jdbcTemplate.update(sqlQuery,
                    userId1,
                    userId2);
        } else throw new UserNotFoundException();

        return getUsersById(userId1);

    }


    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return getUsersById(resultSet.getLong("FRIEND_ID"));

    }

}
