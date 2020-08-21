package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
        final Map<String, Object> params = new HashMap<>(2);
        params.put("NAME", book.getName());
        params.put("GENRE_ID", addGenre(book.getGenre()));
        jdbc.update("INSERT INTO BOOKS (NAME, GENRE_ID) VALUES (:NAME, :GENRE_ID)", params);
        long bookId = jdbc.queryForObject("CALL SCOPE_IDENTITY()", new HashMap<>(0), Long.class);
        addAuthors(bookId, book);
        return bookId;
    }

    private void addAuthors(long bookId, Book book) {
        deleteBookAuthorRelations(book);
        book.getAuthors().forEach(author -> {
            long authorId = author.getId();
            if (authorId == 0) {
                Optional<Author> authorByName = authorDao.getByName(author.getName());
                if (authorByName.isEmpty()) {
                    authorId = authorDao.insert(author);
                } else {
                    authorId = authorByName.get().getId();
                }
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

    @Override
    public void update(Book book) {
        if (book.getId() == 0) {
            throw new BookException("id is 0");
        }
        final Map<String, Object> params = new HashMap<>(3);
        params.put("ID", book.getId());
        params.put("NAME", book.getName());
        params.put("GENRE_ID", addGenre(book.getGenre()));
        jdbc.update("UPDATE BOOKS SET NAME = :NAME, GENRE_ID = :GENRE_ID WHERE ID = :ID", params);
        addAuthors(book.getId(), book);
    }

    @Override
    public void delete(Book book) {
        if (book.getId() == 0) {
            throw new BookException("id is 0");
        }
        final Map<String, Object> params = new HashMap<>(1);
        params.put("ID", book.getId());
        jdbc.update("DELETE FROM BOOKS WHERE ID = :ID", params);
        deleteBookAuthorRelations(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("ID", id);
        try {
            Map<Long, Book> books = jdbc.query(
                    "SELECT B.ID AS BOOK_ID, B.NAME AS BOOK_NAME, A.ID AS AUTHOR_ID, A.NAME AS AUTHOR_NAME, G.ID AS GENRE_ID, G.NAME AS GENRE_NAME FROM BOOKS B " +
                            "LEFT JOIN BOOKS_AUTHORS BA ON B.ID = BA.BOOK_ID " +
                            "LEFT JOIN AUTHORS A ON BA.AUTHOR_ID = A.ID " +
                            "LEFT JOIN GENRES G on B.GENRE_ID = G.ID " +
                            "WHERE B.ID = :ID",
                    params, new BookResultSetExtractor());
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

    @Override
    public void insertBookAuthorRelation(long bookId, long authorId) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("BOOK_ID", bookId);
        params.put("AUTHOR_ID", authorId);
        jdbc.update("INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (:BOOK_ID, :AUTHOR_ID)", params);
    }

    @Override
    public void deleteBookAuthorRelations(Book book) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("BOOK_ID", book.getId());
        jdbc.update("DELETE FROM BOOKS_AUTHORS WHERE BOOK_ID = :BOOK_ID", params);
    }

}
