package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Author(resultSet.getString(1));
    }

}
