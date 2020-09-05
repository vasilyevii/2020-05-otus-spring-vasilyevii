package ru.otus.repositories;

import ru.otus.models.Genre;

import java.util.List;

public interface GenreRepositoryJpa {
    List<Genre> findByName(String name);
}
