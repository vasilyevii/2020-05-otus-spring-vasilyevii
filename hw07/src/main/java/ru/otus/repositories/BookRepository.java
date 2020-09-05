package ru.otus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
