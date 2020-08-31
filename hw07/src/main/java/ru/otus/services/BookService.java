package ru.otus.services;

import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(long bookId, String bookName, String authorName, String genreName);
    Optional<Book> findBookById(long id);
    void deleteBookById(long id);
    List<Book> findAllBooks();

    Comment addComment(Comment comment);
    List<Comment> findAllCommentsByBookId(long bookId);
    void deleteAllCommentsByBookId(long bookId);
}
