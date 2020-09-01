package ru.otus.repositories;

import ru.otus.models.Comment;

import java.util.List;

public interface CommentRepositoryJpa {
    Comment addComment(Comment comment);
    List<Comment> findAllCommentsByBookId(long bookId);
    void deleteAllCommentsByBookId(long bookId);
}
