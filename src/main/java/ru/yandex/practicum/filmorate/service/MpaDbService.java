package ru.yandex.practicum.filmorate.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service("mpaDbService")
public class MpaDbService {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Mpa> getMpa() {
        String sqlQuery = "select ID, RATING from MPA";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    public Mpa getMpaById(Long mpaId) {
        String sqlQuery = "select ID, RATING " +
                "from MPA where ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, mpaId);
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFoundException();
        }
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        Mpa mpa = new Mpa();
        mpa.setId(resultSet.getInt("ID"));
        mpa.setName(resultSet.getString("RATING"));
        return mpa;
    }

}
