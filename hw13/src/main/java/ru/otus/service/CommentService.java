package ru.otus.service;

import ru.otus.dto.CommentDto;
import ru.otus.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);
    List<Comment> findAllCommentsByBookId(long bookId);
    void deleteAllCommentsByBookId(long bookId);
    CommentDto CommentToCommentDto(Comment comment);
    Comment commentDtoToComment(CommentDto commentDto);
}
