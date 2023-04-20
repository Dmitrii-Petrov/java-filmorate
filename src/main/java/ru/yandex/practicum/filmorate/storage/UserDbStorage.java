package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Component("userDbStorage")
@Repository
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("ID");
        user.setId(simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue());

    }

    @Override
    public void update(User user) {

        String sqlQuery = "update USERS set " +
                "EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? " +
                "where ID = ?";


        if ((jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId())) == 0) throw new UserNotFoundException();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User getUser(Long id) {
        String sqlQuery = "select ID, EMAIL, LOGIN, NAME, BIRTHDAY " +
                "from USERS where ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();

        }
    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = "select ID, EMAIL, LOGIN, NAME, BIRTHDAY from USERS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public List<User> getUserList(Set<Long> userIdList) {
        List<User> users = new ArrayList<>();
        for (Long l : userIdList) {
            users.add(getUser(l));
        }
        return users;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return User.builder()
                .id(resultSet.getLong("ID"))
                .email(resultSet.getString("EMAIL"))
                .login(resultSet.getString("LOGIN"))
                .name(resultSet.getString("NAME"))
                .birthday(LocalDate.parse(resultSet.getString("BIRTHDAY"), dateTimeFormatter))
                .build();
    }
}
