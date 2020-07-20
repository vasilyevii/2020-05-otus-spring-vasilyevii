package ru.otus.dao;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    long insert(Book book);
    void update(Book book);
    void delete(Book book);
    Optional<Book> getById(long id);
    List<Book> getAll();

    void insertBookAuthorRelation(long bookId, String authorName);
    void deleteBookAuthorRelations(Book book);
    List<Author> getBookAuthors(Book book);

    void insertBookGenreRelation(long bookId, String genreName);
    void deleteBookGenreRelations(Book book);
    List<Genre> getBookGenres(Book book);

}
