package ru.otus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.BookException;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public long addBook(Book book) {

        long id = book.getId();

        if (id == 0) {
            id = bookDao.insert(book);
        } else {
            bookDao.update(book);
            bookDao.deleteBookAuthorRelations(book);
            bookDao.deleteBookGenreRelations(book);
        }

        long finalId = id;
        book.getAuthors().forEach(author -> {
            if (authorDao.getByName(author.getName()).isEmpty()) {
                authorDao.insert(author);
            };
            bookDao.insertBookAuthorRelation(finalId, author.getName());
        });

        book.getGenres().forEach(genre -> {
            if (genreDao.getByName(genre.getName()).isEmpty()) {
                genreDao.insert(genre);
            };
            bookDao.insertBookGenreRelation(finalId, genre.getName());
        });

        return id;
    }

    @Override
    public void deleteBook(Book book) {
        if (book.getId() != 0) {
            bookDao.delete(book);
            bookDao.deleteBookAuthorRelations(book);
            bookDao.deleteBookGenreRelations(book);
        }
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> bookOptional = bookDao.getById(id);
        if (bookOptional.isEmpty()) {
            throw new BookException("Book with id " + id + " was not found");
        }
        Book book = bookOptional.get();
        bookDao.getBookAuthors(book).forEach(book::addAuthor);
        bookDao.getBookGenres(book).forEach(book::addGenre);
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookDao.getAll();
        books.forEach(book -> {
            bookDao.getBookAuthors(book).forEach(book::addAuthor);
            bookDao.getBookGenres(book).forEach(book::addGenre);
        });
        return books;
    }

}
