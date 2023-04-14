package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {


    void save(Film film);

    void update(Film film);

    void delete(Long id);

    Film getFilm(Long id);

    ArrayList<Film> getFilms();
}