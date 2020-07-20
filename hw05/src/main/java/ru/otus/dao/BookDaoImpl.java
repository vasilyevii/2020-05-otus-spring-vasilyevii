package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public long insert(Book book) {
        Long id = book.getId();
        if (id != 0) {
            throw new BookException("id is not 0");
        }
        id = jdbc.query("select max(id) from books", rs -> rs.next() ? rs.getLong(1) : 1L);
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", ++id);
        params.put("name", book.getName());
        jdbc.update("insert into books (id, name) values (:id, :name)", params);
        return id;
    }

    @Override
    public void update(Book book) {
        if (book.getId() == 0) {
            throw new BookException("id is 0");
        }
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", book.getId());
        params.put("name", book.getName());
        jdbc.update("update books set name = :name where id = :id", params);
    }

    @Override
    public void delete(Book book) {
        if (book.getId() == 0) {
            throw new BookException("id is 0");
        }
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", book.getId());
        jdbc.update("delete from books where id = :id", params);
    }

    @Override
    public Optional<Book> getById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("select * from books where id = :id", params, new BookMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select * from books", new BookMapper());
    }

    @Override
    public void insertBookAuthorRelation(long bookId, String authorName) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("book_id", bookId);
        params.put("author", authorName);
        jdbc.update("insert into books_authors (book_id, author) values (:book_id, :author)", params);
    }

    @Override
    public void deleteBookAuthorRelations(Book book) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("book_id", book.getId());
        jdbc.update("delete from books_authors where book_id = :book_id", params);
    }

    @Override
    public List<Author> getBookAuthors(Book book) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", book.getId());
        return jdbc.query(
                "select author from books_authors where book_id = :id",
                params, new AuthorMapper());
    }

    @Override
    public void insertBookGenreRelation(long bookId, String genreName) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("book_id", bookId);
        params.put("genre", genreName);
        jdbc.update("insert into books_genres (book_id, genre) values (:book_id, :genre)", params);
    }

    @Override
    public void deleteBookGenreRelations(Book book) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("book_id", book.getId());
        jdbc.update("delete from books_genres where book_id = :book_id", params);
    }

    @Override
    public List<Genre> getBookGenres(Book book) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", book.getId());
        return jdbc.query(
                "select genre from books_genres where book_id = :id",
                params, new GenreMapper());
    }

}
