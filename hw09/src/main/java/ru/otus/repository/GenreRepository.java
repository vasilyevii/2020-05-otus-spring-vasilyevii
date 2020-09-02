package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.model.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByName(String name);
}
