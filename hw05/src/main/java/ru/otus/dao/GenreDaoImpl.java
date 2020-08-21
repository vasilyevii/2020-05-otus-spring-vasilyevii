package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public long insert(Genre genre) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("NAME", genre.getName());
        jdbc.update("INSERT INTO GENRES (NAME) VALUES (:NAME)", params);
        return jdbc.queryForObject("CALL SCOPE_IDENTITY()", new HashMap<>(0), Long.class);
    }

    @Override
    public Optional<Genre> getByName(String name) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("NAME", name);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM GENRES WHERE NAME = :NAME", params, new GenreMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}