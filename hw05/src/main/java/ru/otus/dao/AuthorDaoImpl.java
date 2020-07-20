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
    public void insert(Author author) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", author.getName());
        jdbc.update("insert into authors (name) values (:name)", params);
    }

    @Override
    public Optional<Author> getByName(String name) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("select * from authors where name = :name", params, new AuthorMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}