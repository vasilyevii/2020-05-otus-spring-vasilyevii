package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import reactor.core.publisher.Flux;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.Arrays;

import static java.util.Collections.singletonList;

@SpringBootApplication
public class DemoHw11 {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoHw11.class);

        BookRepository bookRepo = context.getBean(BookRepository.class);
        AuthorRepository authorRepo = context.getBean(AuthorRepository.class);
        GenreRepository genreRepo = context.getBean(GenreRepository.class);

        Flux.zip(
                authorRepo.save(new Author(null, "Pushkin")),
                authorRepo.save(new Author(null, "Lermontov")),
                authorRepo.save(new Author(null, "Tolstoy")))
                .zipWith(genreRepo.save(new Genre(null, "Classic")))
                .subscribe(t ->
                        bookRepo.saveAll(Arrays.asList(
                                new Book(null, "Boris Godunov", singletonList(t.getT1().getT1()), t.getT2()),
                                new Book(null, "A Hero of Our Time", singletonList(t.getT1().getT2()), t.getT2()),
                                new Book(null, "War and Peace", singletonList(t.getT1().getT3()), t.getT2())))
                                .subscribe(System.out::println)
                );
    }
}
