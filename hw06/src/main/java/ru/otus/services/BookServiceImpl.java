package ru.otus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.repositories.BookRepositoryJpa;
import ru.otus.repositories.CommentRepositoryJpa;
import ru.otus.repositories.GenreRepositoryJpa;


import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepositoryJpa bookRepo;

    @Autowired
    GenreRepositoryJpa genreRepo;

    @Autowired
    CommentRepositoryJpa commentRepo;

    @Override
    @Transactional
    public Book save(Book book) {
        return bookRepo.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return bookRepo.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByName(String name) {
        return bookRepo.findByName(name);
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        return commentRepo.addComment(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsByBookId(long bookId) {
        return commentRepo.findAllCommentsByBookId(bookId);
    }

    @Override
    @Transactional
    public void deleteAllCommentsByBookId(long bookId) {
        commentRepo.deleteAllCommentsByBookId(bookId);
    }
}
