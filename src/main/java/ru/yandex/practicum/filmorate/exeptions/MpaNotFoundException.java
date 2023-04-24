package ru.yandex.practicum.filmorate.exeptions;

public class MpaNotFoundException extends IllegalArgumentException {
    public MpaNotFoundException() {
        super("Такого рейтинга не существует");
    }
}
