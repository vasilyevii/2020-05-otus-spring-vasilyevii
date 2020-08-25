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
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcTemplate jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public long insert(Book book) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("NAME", book.getName())
                .addValue("GENRE_ID", addGenre(book.getGenre()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO BOOKS (NAME, GENRE_ID) VALUES (:NAME, :GENRE_ID)", params, keyHolder);
        long bookId = keyHolder.getKey().longValue();
        addBookAuthors(bookId, book.getAuthors());
        return bookId;
    }

    @Override
    public void update(Book book) {
        if (book.getId() == 0) {
            throw new BookException("id is 0");
        }
        jdbc.update("UPDATE BOOKS SET NAME = :NAME, GENRE_ID = :GENRE_ID WHERE ID = :ID", Map.of(
                "ID", book.getId(), "NAME", book.getName(), "GENRE_ID", addGenre(book.getGenre())));
        addBookAuthors(book.getId(), book.getAuthors());
    }

    @Override
    public void delete(Book book) {
        if (book.getId() == 0) {
            throw new BookException("id is 0");
        }
        deleteBookAuthorRelations(book.getId());
        jdbc.update("DELETE FROM BOOKS WHERE ID = :ID", Map.of("ID", book.getId()));
    }

    @Override
    public Optional<Book> getById(long id) {
        try {
            Map<Long, Book> books = jdbc.query(
                    "SELECT B.ID AS BOOK_ID, B.NAME AS BOOK_NAME, A.ID AS AUTHOR_ID, A.NAME AS AUTHOR_NAME, G.ID AS GENRE_ID, G.NAME AS GENRE_NAME FROM BOOKS B " +
                            "LEFT JOIN BOOKS_AUTHORS BA ON B.ID = BA.BOOK_ID " +
                            "LEFT JOIN AUTHORS A ON BA.AUTHOR_ID = A.ID " +
                            "LEFT JOIN GENRES G on B.GENRE_ID = G.ID " +
                            "WHERE B.ID = :ID",
                    Map.of("ID", id), new BookResultSetExtractor());
            return Optional.ofNullable(books.get(id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        Map<Long, Book> books = jdbc.query(
                "SELECT B.ID AS BOOK_ID, B.NAME AS BOOK_NAME, A.ID AS AUTHOR_ID, A.NAME AS AUTHOR_NAME, G.ID AS GENRE_ID, G.NAME AS GENRE_NAME FROM BOOKS B " +
                        "LEFT JOIN BOOKS_AUTHORS BA ON B.ID = BA.BOOK_ID " +
                        "LEFT JOIN AUTHORS A ON BA.AUTHOR_ID = A.ID " +
                        "LEFT JOIN GENRES G on B.GENRE_ID = G.ID ",
                new BookResultSetExtractor());
        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    private void insertBookAuthorRelation(long bookId, long authorId) {
        jdbc.update("INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (:BOOK_ID, :AUTHOR_ID)",
                Map.of("BOOK_ID", bookId,"AUTHOR_ID", authorId));
    }

    private void deleteBookAuthorRelations(long bookId) {
        jdbc.update("DELETE FROM BOOKS_AUTHORS WHERE BOOK_ID = :BOOK_ID",
                Map.of("BOOK_ID", bookId));
    }

    private void addBookAuthors(long bookId, List<Author> authors) {
        deleteBookAuthorRelations(bookId);
        authors.forEach(author -> {
            long authorId = author.getId();
            if (authorId == 0) {
                authorId = authorDao.getByName(author.getName()).map(Author::getId).orElse(authorDao.insert(author));
            }
            insertBookAuthorRelation(bookId, authorId);
        });
    }

    private long addGenre(Genre genre) {
        long genreId = genre.getId();
        if (genreId == 0) {
            Optional<Genre> genreByName = genreDao.getByName(genre.getName());
            if (genreByName.isEmpty()) {
                genreId = genreDao.insert(genre);
            } else {
                genreId = genreByName.get().getId();
            }
        }
        return genreId;
    }

}
