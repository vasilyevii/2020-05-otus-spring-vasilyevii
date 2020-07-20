package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.Optional;

public interface AuthorDao {
    void insert(Author author);
    Optional<Author> getByName(String name);
}
