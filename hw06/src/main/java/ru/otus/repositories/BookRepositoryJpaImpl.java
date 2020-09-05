package ru.otus.repositories;

import ru.otus.models.Book;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book s where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> findByName(String name) {
        TypedQuery<Book> query = em.createQuery(
                "select s from Book s where s.name = :name", Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
