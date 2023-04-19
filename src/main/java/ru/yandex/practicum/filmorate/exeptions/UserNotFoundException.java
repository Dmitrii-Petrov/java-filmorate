package ru.yandex.practicum.filmorate.exeptions;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException() {
        super("Такого юзера не существует");
    }
}
