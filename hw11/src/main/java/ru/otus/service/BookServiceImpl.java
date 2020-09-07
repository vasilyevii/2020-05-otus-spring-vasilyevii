package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.BookDto;
import ru.otus.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;

    @Override
    public Flux<BookDto> findAll() {
        return bookRepo.findAll().map(BookDto::bookToBookDto);
    }

    @Override
    public Mono<BookDto> save(Mono<BookDto> bookDtoMono) {
        return bookDtoMono.flatMap(bookDto -> bookRepo.save(BookDto.bookDtoToBook(bookDto)))
                .flatMap(book -> Mono.just(BookDto.bookToBookDto(book)));
    }
}
