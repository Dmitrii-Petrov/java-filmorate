package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.List;


@Service("genreService")
public class GenreService {

    GenreDbStorage genreDbStorage;


    @Autowired
    public GenreService(@Qualifier("genreDbStorage") GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public GenreDbStorage getGenreDbStorage() {
        return genreDbStorage;
    }

    public List<Genre> getGenre() {
        return getGenreDbStorage().getGenre();
    }

    public Genre getGenreById(Integer genreId) {
        return getGenreDbStorage().getGenreById(genreId);
    }

}
