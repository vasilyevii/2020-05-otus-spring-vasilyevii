package ru.otus.repositories;

import ru.otus.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {
    Book save(Book book);
    Optional<Book> findById(long id);
    void deleteById(long id);
    List<Book> findAll();
    List<Book> findByName(String name);
}
