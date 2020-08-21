package ru.otus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.dao.BookException;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public Book addBook(Book book) {
        long id = book.getId();
        if (id == 0) {
            id = bookDao.insert(book);
        } else {
            bookDao.update(book);
        }
        return getBookById(id);
    }

    @Override
    public void deleteBook(Book book) {
        if (book.getId() != 0) {
            bookDao.delete(book);
        }
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> bookOptional = bookDao.getById(id);
        if (bookOptional.isEmpty()) {
            throw new BookException("Book with id " + id + " was not found");
        }
        Book book = bookOptional.get();
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookDao.getAll();
        return books;
    }

}
