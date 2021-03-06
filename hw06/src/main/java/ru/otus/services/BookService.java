package ru.otus.services;

import ru.otus.models.Book;
import ru.otus.models.Comment;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book save(Book book);
    Optional<Book> findById(long id);
    void deleteById(long id);
    List<Book> findAll();

    Comment addComment(Comment comment);
    List<Comment> findAllCommentsByBookId(long bookId);
    void deleteAllCommentsByBookId(long bookId);

}
