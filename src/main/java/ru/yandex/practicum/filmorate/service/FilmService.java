package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class FilmService {

    public void addLike(Film film, Long id) {
        film.getLikes().add(id);
    }

    public void removeLike(Film film, Long id) {
        if (!film.getLikes().contains(id)) {
            throw new ValidationException("такой юзер лайк не ставил");
        }
        film.getLikes().remove(id);
    }

    public ArrayList<Film> getMostLikedFilms(FilmStorage filmStorage, Long size) {
        ArrayList<Film> list = filmStorage.getFilms();
        list.sort((lhs, rhs) -> rhs.getLikes().size() - lhs.getLikes().size());
        while (list.size() > Objects.requireNonNullElse(size, 10L)) {
            list.remove(list.size() - 1);
        }
        return list;
    }

}
