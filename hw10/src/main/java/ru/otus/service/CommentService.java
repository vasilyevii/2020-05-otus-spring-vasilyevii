package ru.otus.service;

import ru.otus.dto.CommentDto;
import ru.otus.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);
    List<Comment> findAllCommentsByBookId(String bookId);
    void deleteAllCommentsByBookId(String bookId);
    CommentDto CommentToCommentDto(Comment comment);
    Comment commentDtoToComment(CommentDto commentDto);
}
