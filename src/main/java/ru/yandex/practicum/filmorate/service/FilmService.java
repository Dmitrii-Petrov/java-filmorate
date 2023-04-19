package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FilmService {

    FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    public ArrayList<Film> getFilms() {
        return getFilmStorage().getFilms();
    }

    public Film getFilmById(Long filmId) {
        return getFilmStorage().getFilm(filmId);

    }

    public Film create(Film film) {
        getFilmStorage().save(film);
        return film;
    }

    public Film update(Film film) {
        getFilmStorage().update(film);
        return film;
    }


    public Film addLike(Long filmId, Long id) {
        getFilmById(filmId).getLikes().add(id);
        return getFilmById(filmId);
    }

    public Film removeLike(Long filmId, Long id) {
        if (!getFilmById(filmId).getLikes().contains(id)) {
            throw new UserNotFoundException();
        }
        getFilmById(filmId).getLikes().remove(id);
        return getFilmById(filmId);
    }

    public List<Film> getMostLikedFilms(Integer size) {
        ArrayList<Film> list = filmStorage.getFilms();
        list.sort((lhs, rhs) -> rhs.getLikes().size() - lhs.getLikes().size());
        return list.subList(0, Integer.min(list.size(), Objects.requireNonNullElse(size, 10)));
    }

}
