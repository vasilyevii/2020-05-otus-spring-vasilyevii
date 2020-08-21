package ru.otus.dao;

import ru.otus.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookDao {
    long insert(Book book);
    void update(Book book);
    void delete(Book book);
    Optional<Book> getById(long id);
    List<Book> getAll();
    void insertBookAuthorRelation(long bookId, long authorId);
    void deleteBookAuthorRelations(Book book);
}
