package ru.otus.service;

import ru.otus.dto.BookDto;
import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book);
    Optional<Book> findBookById(long id);
    void deleteBookById(long id);
    List<Book> findAllBooks();
    BookDto bookToBookDto(Book book);
    Book bookDtoToBook(BookDto bookDto);
}
