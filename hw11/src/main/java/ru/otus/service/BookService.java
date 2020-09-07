package ru.otus.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.BookDto;

public interface BookService {
    Flux<BookDto> findAll();
    Mono<BookDto> save(Mono<BookDto> bookDto);
}
