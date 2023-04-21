package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("ID");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue());
    }

    @Override
    public void update(Film film) {
        String sqlQuery = "update FILMS set " + "NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATING = ? " + "where ID = ?";
        if ((jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId())) == 0)
            throw new FilmNotFoundException();
    }

    @Override
    public boolean delete(Long id) {
        String sqlQuery = "delete from FILMS where ID = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    @Override
    public Film getFilm(Long id) {
        String sqlQuery = "select ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING " +
                "from FILMS where ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException();
        }
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "select ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING from FILMS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Film film = Film.builder()
                .id(resultSet.getLong("ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(LocalDate.parse(resultSet.getString("RELEASE_DATE"), dateTimeFormatter))
                .duration(resultSet.getInt("DURATION"))
                .build();
        film.getMpa().setId(resultSet.getInt("RATING"));
        return film;
    }
}
