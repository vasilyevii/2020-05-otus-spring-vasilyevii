package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.BookDto;
import ru.otus.model.Book;
import ru.otus.service.BookService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> listPage() {
        return bookService.findAllBooks()
                .stream()
                .map(bookService::bookToBookDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/books")
    public BookDto addBook(BookDto bookDto) {
        Book book = bookService.saveBook(bookService.bookDtoToBook(bookDto));
        return bookService.bookToBookDto(book);
    }
}
