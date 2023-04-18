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

    public void addLike(Film film, Long id) {
        film.getLikes().add(id);
    }

    public void removeLike(Film film, Long id) {
        if (!film.getLikes().contains(id)) {
            throw new UserNotFoundException();
        }
        film.getLikes().remove(id);
    }

    public List<Film> getMostLikedFilms(FilmStorage filmStorage, Integer size) {
        ArrayList<Film> list = filmStorage.getFilms();
        list.sort((lhs, rhs) -> rhs.getLikes().size() - lhs.getLikes().size());
        return list.subList(0, Integer.min(list.size(), Objects.requireNonNullElse(size, 10)));
    }

}
