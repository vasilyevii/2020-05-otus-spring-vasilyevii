package ru.otus.services;

import ru.otus.models.Book;
import ru.otus.models.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(String bookName, String authorName, String genreName);
    Optional<Book> findBookById(String id);
    void deleteBookById(String id);
    List<Book> findAllBooks();

    Comment addComment(Comment comment);
    List<Comment> findAllCommentsByBookId(String bookId);
    void deleteAllCommentsByBookId(String bookId);
}
