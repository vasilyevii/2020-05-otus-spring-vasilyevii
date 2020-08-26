package ru.otus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.repositories.BookRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepositoryJpa bookRepo;

    @Override
    public Book save(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookRepo.findById(id);
    }

    @Override
    public void deleteById(long id) {
        bookRepo.deleteById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepo.findByName(name);
    }

    @Override
    public Comment addComment(Comment comment) {
        return bookRepo.addComment(comment);
    }

    @Override
    public List<Comment> findAllCommentsByBookId(long bookId) {
        return bookRepo.findAllCommentsByBookId(bookId);
    }

    @Override
    public void deleteAllCommentsByBookId(long bookId) {
        bookRepo.deleteAllCommentsByBookId(bookId);
    }
}
