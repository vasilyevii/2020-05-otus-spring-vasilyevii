package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByBook_Id(String id);
    void deleteAllByBook_Id(String bookId);
}
