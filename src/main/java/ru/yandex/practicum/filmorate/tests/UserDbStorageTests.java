package ru.yandex.practicum.filmorate.tests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTests {
    private final UserDbStorage userStorage;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        UserDbStorageTests.user = User.builder().email("test@email.com").login("test login").name("test name").birthday(LocalDate.of(2000, 1, 1)).build();
    }

    @Test
    public void testFindUserById() {
        userStorage.save(user);

        User testUser = userStorage.getUser(user.getId());

        Assertions.assertEquals(testUser.getId(), user.getId());
        Assertions.assertEquals(testUser.getBirthday(), user.getBirthday());
        Assertions.assertEquals(testUser.getEmail(), user.getEmail());
        Assertions.assertEquals(testUser.getName(), user.getName());
        Assertions.assertEquals(testUser.getLogin(), user.getLogin());
    }

    @Test
    public void testUpdateUser() {
        userStorage.save(user);
        user.setName("updated test name");
        userStorage.update(user);

        User testUser = userStorage.getUser(user.getId());

        Assertions.assertEquals(testUser.getId(), user.getId());
        Assertions.assertEquals(testUser.getBirthday(), user.getBirthday());
        Assertions.assertEquals(testUser.getEmail(), user.getEmail());
        Assertions.assertEquals(testUser.getName(), user.getName());
        Assertions.assertEquals(testUser.getLogin(), user.getLogin());
    }

    @Test
    public void testDeleteUser() {
        userStorage.save(user);

        userStorage.delete(user.getId());

        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            userStorage.getUser(user.getId());
        });
    }
} 