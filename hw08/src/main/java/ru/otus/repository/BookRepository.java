package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}
