package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @PostFilter("hasRole('ADMIN') || hasPermission(filterObject, 'READ') || hasPermission(filterObject.genre, 'READ')")
    List<Book> findAll();

    @PostAuthorize("hasPermission(returnObject.get(), 'READ') || hasPermission(returnObject.get().genre, 'READ')")
    Optional<Book> findById(long bookId);
}
