package ru.otus;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@EnableMongoRepositories
@SpringBootApplication
public class DemoMongoDB {

    @Autowired
    private BookRepository bookRepo;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoMongoDB.class);
        BookRepository bookRepo = context.getBean(BookRepository.class);

        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1, "Pushkin"));
        authors.add(new Author(2, "Lermontov"));
        Genre genre = new Genre(1, "Horror");
        Book pushkin = bookRepo.save(new Book("Pushkin", authors, genre));
        System.out.println(pushkin);

        System.out.println(bookRepo.findById(pushkin.getId()));

//
//        repository.save(new Person("Pushkin"));
//
//        Thread.sleep(3000);
//
//        repository.findAll().forEach(p -> System.out.println(p.getName()));
    }

}
