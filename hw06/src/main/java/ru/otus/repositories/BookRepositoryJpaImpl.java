package ru.otus.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.models.Author;
import ru.otus.models.Book;
import org.springframework.stereotype.Repository;
import ru.otus.models.Comment;
import ru.otus.models.Genre;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    GenreRepositoryJpa genreRepo;

    @Autowired
    AuthorRepositoryJpa authorRepo;

    @Override
    public Book save(Book book) {
        List<Genre> genreByName = genreRepo.findByName(book.getGenre().getName());
        if (!genreByName.isEmpty()) {
            book.setGenre(genreByName.get(0));
        }
        List<Author> authorList = book.getAuthorList();
        for (int i = 0; i < authorList.size(); i++) {
            List<Author> authorByName = authorRepo.findByName(authorList.get(i).getName());
            if (!authorByName.isEmpty()) {
                authorList.set(i, authorByName.get(0));
            }
        }
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
