package ru.otus.repositories;

import ru.otus.models.Author;

import java.util.List;

public interface AuthorRepositoryJpa {
    List<Author> findByName(String name);
}
