package ru.otus.services;

import ru.otus.domain.Book;

import java.util.List;

public interface BookService {
    long addBook(Book book);
    void deleteBook(Book book);
    Book getBookById(long id);
    List<Book> getAllBooks();
}
