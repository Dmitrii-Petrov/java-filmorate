package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Data
@Component
public class InMemoryFilmStorage implements FilmStorage {
    HashMap<Long, Film> filmRepository = new HashMap<>();
    long generatorId = 0;

    public long generateId() {
        return ++generatorId;
    }

    @Override
    public void save(Film film) {
        film.setId(generateId());
        filmRepository.put(film.getId(), film);
    }

    @Override
    public void update(Film film) {
        if (!filmRepository.containsKey(film.getId())) {
            throw new NoSuchElementException("фильм не существует");
        }
        filmRepository.put(film.getId(), film);
    }

    @Override
    public void delete(Long id) {
        if (!filmRepository.containsKey(id)) {
            throw new NoSuchElementException("фильм не существует");
        }
        filmRepository.remove(id);
    }


    @Override
    public Film getFilm(Long id) {
        if (!filmRepository.containsKey(id)) {
            throw new ValidationException("фильма не существует");
        }
        return filmRepository.get(id);
    }

    @Override
    public ArrayList<Film> getFilms() {
        return new ArrayList<>(filmRepository.values());
    }


}
