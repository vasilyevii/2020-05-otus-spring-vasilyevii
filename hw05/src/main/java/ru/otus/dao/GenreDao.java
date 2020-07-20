package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.Optional;

public interface GenreDao {
    void insert(Genre genre);
    Optional<Genre> getByName(String name);
}