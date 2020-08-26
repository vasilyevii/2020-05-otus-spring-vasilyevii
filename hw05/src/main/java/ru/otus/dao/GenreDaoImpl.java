package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public long insert(Genre genre) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("NAME", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO GENRES (NAME) VALUES (:NAME)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Genre> getByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM GENRES WHERE NAME = :NAME",
                            Map.of("NAME", name), new GenreMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}