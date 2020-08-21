package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public long insert(Author author) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("NAME", author.getName());
        jdbc.update("INSERT INTO AUTHORS (NAME) VALUES (:NAME)", params);
        return jdbc.queryForObject("CALL SCOPE_IDENTITY()", new HashMap<>(0), Long.class);
    }

    @Override
    public Optional<Author> getByName(String name) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("NAME", name);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM AUTHORS WHERE NAME = :NAME", params, new AuthorMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}