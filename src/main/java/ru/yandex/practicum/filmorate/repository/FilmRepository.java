package ru.yandex.practicum.filmorate.repository;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.servise.ValidateService;

import java.util.HashMap;

@Data
public class FilmRepository {
    HashMap<Long, Film> filmRepository = new HashMap<>();
    ValidateService validateService = new ValidateService();
    long generatorId = 0;

    public long generateId() {
        return ++generatorId;
    }

    public void save(Film film) {
        film.setId(generateId());
        validateService.validateFilm(film);
        filmRepository.put(film.getId(), film);
    }

    public void update(Film film) {
        if (!filmRepository.containsKey(film.getId())) {
            throw new RuntimeException("фильм не существует");
        }
        filmRepository.put(film.getId(), film);
    }


}
