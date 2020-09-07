package ru.otus.route;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@RequiredArgsConstructor
public class BookRouter {

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookService bookService) {
        return route()
                .GET("/api/books", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(bookService.findAll(), BookDto.class))
                .POST("/api/books", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(BookDto.class)
                                .flatMap(bookDto -> bookService.save(Mono.just(bookDto)))
                                .flatMap(bookDto -> ok().contentType(APPLICATION_JSON)
                                        .body(BodyInserters.fromValue(bookDto))))
                .build();
    }
}
