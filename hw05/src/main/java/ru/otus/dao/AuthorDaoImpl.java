package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public long insert(Author author) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("NAME", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO AUTHORS (NAME) VALUES (:NAME)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Author> getByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM AUTHORS WHERE NAME = :NAME",
                            Map.of("NAME", name), new AuthorMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}