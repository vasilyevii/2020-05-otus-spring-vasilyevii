package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        return new Book(resultSet.getLong("id"), resultSet.getString("name"), authors, genres);
    }

}
