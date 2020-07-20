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
    public void insert(Genre genre) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", genre.getName());
        jdbc.update("insert into genres (name) values (:name)", params);
    }

    @Override
    public Optional<Genre> getByName(String name) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("select * from genres where name = :name", params, new GenreMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}