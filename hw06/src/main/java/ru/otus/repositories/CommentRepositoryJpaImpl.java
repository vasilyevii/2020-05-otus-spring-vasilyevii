package ru.otus.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment addComment(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public List<Comment> findAllCommentsByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :book_id", Comment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public void deleteAllCommentsByBookId(long bookId) {
        Query query = em.createQuery("delete from Comment c where c.book.id = :book_id");
        query.setParameter("book_id", bookId);
        query.executeUpdate();
    }
}
