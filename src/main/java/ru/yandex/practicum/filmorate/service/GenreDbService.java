package ru.yandex.practicum.filmorate.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exeptions.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("genreDbService")
public class GenreDbService {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genre> getGenre() {
        String sqlQuery = "select ID, NAME from GENRE";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    public List<Genre> getGenreToFilmById(Long filmId) {
        String sqlQuery = "select FILM_ID, GENRE_ID from FILM_GENRE where FILM_ID = ?";
        List<Genre> list = jdbcTemplate.query(sqlQuery, this::mapRowToFilmGenre, filmId);
        for (Genre g : list) {
            g.setName(getGenreById(g.getId()).getName());
        }
        return list;
    }

    public Genre getGenreById(Integer genreId) {
        String sqlQuery = "select ID, NAME " +
                "from GENRE where ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, genreId);
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException();
        }
    }

    private Genre mapRowToFilmGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("GENRE_ID"));
        return genre;
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("ID"));
        genre.setName(resultSet.getString("NAME"));
        return genre;
    }

    public void updateFilmGenre(Film film) {
        removeGenresFromFilm(film.getId());
        Set<Genre> set = new HashSet<>(film.getGenres());
        for (Genre g : set) {
            addGenreToFilm(film.getId(), g.getId());
        }
    }


    public void addGenreToFilm(Long filmId, Integer genreId) {
        String sqlQuery = "insert into FILM_GENRE (FILM_ID, GENRE_ID) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                filmId,
                genreId);
    }

    public void removeGenresFromFilm(Long filmId) {
        if (filmId > 0) {
            String sqlQuery = "delete from FILM_GENRE where (FILM_ID = ?)";
            jdbcTemplate.update(sqlQuery,
                    filmId);
        } else throw new FilmNotFoundException();
    }


    public void removeGenreFromFilm(Long filmId, Long genreId) {
        if ((filmId > 0) && (genreId > 0)) {
            String sqlQuery = "delete from FILM_GENRE where ((FILM_ID = ?) and (GENRE_ID = ?))";
            jdbcTemplate.update(sqlQuery,
                    filmId,
                    genreId);
        } else throw new FilmNotFoundException();
    }
}
