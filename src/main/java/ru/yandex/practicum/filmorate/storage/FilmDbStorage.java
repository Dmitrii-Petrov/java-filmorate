package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
@Data
@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage{
    @Override
    public void save(Film film) {

    }

    @Override
    public void update(Film film) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Film getFilm(Long id) {
        return null;
    }

    @Override
    public ArrayList<Film> getFilms() {
        return null;
    }
}
