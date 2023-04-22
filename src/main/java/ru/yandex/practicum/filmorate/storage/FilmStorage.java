package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {


    void save(Film film);

    void update(Film film);

    boolean delete(Long id);

    Film getFilm(Long id);

    List<Film> getFilms();

    List<Film> getMostLikedFilms(Integer size);

    Film removeLike(Long filmId, Long id);

    Film addLike(Long filmId, Long id);
}