package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exeptions.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exeptions.MpaNotFoundException;
import ru.yandex.practicum.filmorate.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("filmDbService")
public class FilmDbService extends FilmService {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbService(@Qualifier("filmDbStorage") FilmStorage filmStorage, JdbcTemplate jdbcTemplate) {
        super(filmStorage);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getFilms() {
        List<Film> list = super.getFilms();
        for (Film f : list) {
            updateFilmsMpaGenreNames(f);
        }
        return list;
    }

    @Override
    public Film getFilmById(Long filmId) {
        Film film = super.getFilmById(filmId);
        updateFilmsMpaGenreNames(film);
        return film;
    }

    @Override
    public Film create(Film film) {
        super.create(film);
        updateFilmsMpaGenreNames(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        super.update(film);
        updateFilmsMpaGenreNames(film);
        return film;
    }

    @Override
    public Film addLike(Long filmId, Long userId) {
        if (filmId > 0) {
            String sqlQuery = "insert into FILM_LIKES (FILM_ID, USER_ID) " +
                    "values (?, ?)";

            jdbcTemplate.update(sqlQuery,
                    filmId,
                    userId);
        } else throw new FilmNotFoundException();
        return getFilmById(filmId);
    }

    @Override
    public Film removeLike(Long filmId, Long userId) {
        if (filmId > 0) {
            String sqlQuery = "delete from FILM_LIKES where ((FILM_ID = ?) and (USER_ID = ?))";
            jdbcTemplate.update(sqlQuery,
                    filmId,
                    userId);
        } else throw new UserNotFoundException();
        return getFilmById(filmId);
    }

    @Override

    //todo прописать join таблиц
    public List<Film> getMostLikedFilms(Integer size) {
        List<Film> list = new ArrayList<>();
        String sqlQuery = "select FILM_ID from FILM_LIKES group by FILM_ID order by count(USER_ID) desc";
        for (Long l : jdbcTemplate.query(sqlQuery, this::mapRowToLike)) {
            list.add(getFilmById(l));
        }
        return list.subList(0, Integer.min(list.size(), Objects.requireNonNullElse(size, 10)));
    }

    private Long mapRowToLike(ResultSet resultSet, int i) throws SQLException {

        return resultSet.getLong("FILM_ID");
    }


    public void updateFilmsMpaGenreNames(Film film) {
        updateMpaName(film.getMpa());
        for (Genre g : film.getGenres()) {
            updateGenreName(g);
        }
    }


    private void updateGenreName(Genre genre) {
        String sqlQuery = "select NAME from GENRE where ID = ?";
        try {
            genre.setName(jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, genre.getId()));
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException();
        }
    }

    private String mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getString("NAME");
    }


    private void updateMpaName(Mpa mpa) {
        String sqlQuery = "select RATING from MPA where ID = ?";
        try {
            mpa.setName(jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, mpa.getId()));
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFoundException();
        }
    }

    private String mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getString("RATING");
    }

}
