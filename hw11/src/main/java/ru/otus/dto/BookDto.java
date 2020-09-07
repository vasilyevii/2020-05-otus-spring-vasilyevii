package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private String authorList;
    private String genre;

    public static BookDto bookToBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                book.getAuthorList().stream()
                        .map(Author::getName)
                        .collect(Collectors.joining(";")),
                book.getGenre().getName());
    }

    public static Book bookDtoToBook(BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getName(),
                Arrays.stream(bookDto.getAuthorList().split(";"))
                        .map(str -> new Author(null, str.trim()))
                        .collect(Collectors.toList()),
                new Genre(null, bookDto.getGenre()));
    }
}
