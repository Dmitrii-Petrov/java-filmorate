package ru.yandex.practicum.filmorate.exeptions;

public class GenreNotFoundException extends IllegalArgumentException {
    public GenreNotFoundException() {
        super("Такого жанра не существует");
    }
}
