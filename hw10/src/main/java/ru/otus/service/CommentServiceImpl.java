package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.CommentDto;
import ru.otus.model.Comment;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final BookRepository bookRepo;

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsByBookId(String bookId) {
        return commentRepo.findAllByBook_Id(bookId);
    }

    @Override
    @Transactional
    public void deleteAllCommentsByBookId(String bookId) {
        commentRepo.deleteAllByBook_Id(bookId);
    }

    @Override
    public CommentDto CommentToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getBook().getId(),
                comment.getUserName(),
                comment.getText()
        );
    }

    @Override
    public Comment commentDtoToComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                bookRepo.findById(commentDto.getBookId()).orElseThrow(NotFoundException::new),
                commentDto.getUserName(),
                commentDto.getText()
        );
    }
}
