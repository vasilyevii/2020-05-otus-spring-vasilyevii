package ru.otus.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {

    @Override
    public Map<Long, Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Book> books = new HashMap<>();
        while (rs.next()) {
            long id = rs.getLong("BOOK_ID");
            Book book = books.get(id);
            if (book == null) {
                book = new Book(id, rs.getString("BOOK_NAME"), new ArrayList<>(),
                        new Genre(rs.getLong("GENRE_ID"), rs.getString("GENRE_NAME")));
                books.put(book.getId(), book);
            }
            if (rs.getLong("AUTHOR_ID") != 0) {
                book.addAuthor(new Author(rs.getLong("AUTHOR_ID"), rs.getString("AUTHOR_NAME")));
            }
        }
        return books;
    }
}
