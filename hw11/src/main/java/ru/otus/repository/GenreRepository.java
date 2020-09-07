package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
