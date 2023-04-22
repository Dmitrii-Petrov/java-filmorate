package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
@Data
public class FilmService {

    FilmStorage filmStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getFilms() {
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
        return filmStorage.addLike(filmId, id);
    }

    public Film removeLike(Long filmId, Long id) {
        return filmStorage.removeLike(filmId, id);
    }

    public List<Film> getMostLikedFilms(Integer size) {
        return getFilmStorage().getMostLikedFilms(size);
    }

}
