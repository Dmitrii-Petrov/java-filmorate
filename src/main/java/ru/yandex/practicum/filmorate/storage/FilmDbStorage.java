package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

@Data
@Repository("filmDbStorage")
public class FilmDbStorage implements FilmStorage {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Film film) {
//        String sqlQuery = "insert into films (name, description, release_date, duration, rating) " +
//                "values (?, ?, ?, ?, ?)";
//
//        jdbcTemplate.update(sqlQuery,
//                film.getName(),
//                film.getDescription(),
//                film.getReleaseDate(),
//                film.getDuration(),
//                film.getRating());
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("ID");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue());


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
