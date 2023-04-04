package ru.yandex.practicum.filmorate.repository;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.NoSuchElementException;

@Data
public class FilmRepository {
    HashMap<Long, Film> filmRepository = new HashMap<>();
    long generatorId = 0;

    public long generateId() {
        return ++generatorId;
    }

    public void save(Film film) {
        film.setId(generateId());
        filmRepository.put(film.getId(), film);
    }

    public void update(Film film) {
        if (!filmRepository.containsKey(film.getId())) {
            throw new NoSuchElementException("фильм не существует");
        }
        filmRepository.put(film.getId(), film);
    }


}
