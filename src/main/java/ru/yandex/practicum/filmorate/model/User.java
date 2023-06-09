package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;


@Data
@Builder
public class User {

    Long id;
    @Email
    String email;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$")
    String login;
    @Size(max = 20, message = "максимальная длина имени - 20 символов")
    String name;
    @Past
    LocalDate birthday;
    Set<Long> friends;

    public User(Long id, String email, String login, String name, LocalDate birthday, Set<Long> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        if ((name == null) || (name.isBlank())) {
            this.name = login;
        } else {
            this.name = name;
        }

        this.birthday = birthday;
        this.friends = Objects.requireNonNullElseGet(friends, HashSet::new);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("EMAIL", email);
        values.put("LOGIN", login);
        values.put("NAME", name);
        values.put("BIRTHDAY", birthday);
        return values;
    }
}
