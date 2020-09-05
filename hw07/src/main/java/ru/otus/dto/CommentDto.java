package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.models.Comment;

@Data
@AllArgsConstructor
public class CommentDto {
    private long id;
    private long bookId;
    private String userName;
    private String text;
    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getBook().getId(), comment.getUserName(), comment.getText());
    }
}
