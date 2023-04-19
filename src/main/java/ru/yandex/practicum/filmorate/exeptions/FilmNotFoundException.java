package ru.yandex.practicum.filmorate.exeptions;

public class FilmNotFoundException extends IllegalArgumentException {
    public FilmNotFoundException() {
        super("Такого фильма не существует");
    }
}
