package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exeptions.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exeptions.MpaNotFoundException;
import ru.yandex.practicum.filmorate.exeptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        updateFilmGenre(film);
        updateFilmsMpaGenre(film);
    }

    @Override
    public void update(Film film) {
        String sqlQuery = "update FILMS set " + "NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATING = ? " + "where ID = ?";
        if ((jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId())) != 0) {
            updateFilmGenre(film);
            updateFilmsMpaGenre(film);
        } else {
            throw new FilmNotFoundException();
        }
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
            Film film = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
            assert film != null;
            updateFilmsMpaGenre(film);
            return film;
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException();
        }
    }

    public List<Film> getMostLikedFilms(Integer size) {
        List<Film> list = new ArrayList<>();
        String sqlQuery = "select ID from FILMS left join FILM_LIKES FL on FILMS.ID = FL.FILM_ID group by ID order by count(USER_ID) desc";
        for (Long l : jdbcTemplate.query(sqlQuery, this::mapRowToLike)) {
            list.add(getFilm(l));
        }
        return list.subList(0, Integer.min(list.size(), Objects.requireNonNullElse(size, 10)));
    }

    public Film addLike(Long filmId, Long userId) {
        if (filmId > 0) {
            String sqlQuery = "insert into FILM_LIKES (FILM_ID, USER_ID) " +
                    "values (?, ?)";

            jdbcTemplate.update(sqlQuery,
                    filmId,
                    userId);
        } else throw new FilmNotFoundException();
        return getFilm(filmId);
    }

    public Film removeLike(Long filmId, Long userId) {
        if ((filmId > 0) && (userId > 0)) {
            String sqlQuery = "delete from FILM_LIKES where ((FILM_ID = ?) and (USER_ID = ?))";
            jdbcTemplate.update(sqlQuery,
                    filmId,
                    userId);
            return getFilm(filmId);
        } else throw new UserNotFoundException();
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "select ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING from FILMS";

        List<Film> list = jdbcTemplate.query(sqlQuery, this::mapRowToFilm);

        for (Film f : list) {
            updateFilmsMpaGenre(f);
        }
        return list;
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

    private Long mapRowToLike(ResultSet resultSet, int i) throws SQLException {

        return resultSet.getLong("ID");
    }

    public void updateFilmsMpaGenre(Film film) {
        updateMpaName(film.getMpa());
        film.getGenres().clear();
        for (Genre g : getGenreToFilmById(film.getId())) {
            film.getGenres().add(g);
        }
        for (Genre g : film.getGenres()) {
            updateGenreName(g);
        }
    }

    private void updateGenreName(Genre genre) {
        String sqlQuery = "select NAME from GENRE where ID = ?";
        try {
            genre.setName(jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenreName, genre.getId()));
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException();
        }
    }

    private String mapRowToGenreName(ResultSet resultSet, int rowNum) throws SQLException {
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
