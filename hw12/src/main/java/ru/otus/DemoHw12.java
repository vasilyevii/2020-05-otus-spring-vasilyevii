package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.model.*;
import ru.otus.repository.*;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.UserService;

import java.util.Arrays;

import static java.util.Collections.singletonList;

@SpringBootApplication
public class DemoHw12 {
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(DemoHw12.class);

        BookService bookService = context.getBean(BookService.class);
        CommentService commentService = context.getBean(CommentService.class);
        UserService userService = context.getBean(UserService.class);

        Author pushkin = new Author(null, "Pushkin");
        Author lermontov = new Author(null, "Lermontov");
        Author tolstoy = new Author(null, "Tolstoy");
        Genre classic = new Genre(null, "Classic");

        bookService.saveBook(new Book(null, "Boris Godunov", Arrays.asList(pushkin), classic));
        bookService.saveBook(new Book(null, "A Hero of Our Time", Arrays.asList(lermontov), classic));
        Book war_and_peace = bookService.saveBook(new Book(null, "War and Peace", Arrays.asList(tolstoy), classic));

        commentService.addComment(new Comment(null, war_and_peace, "Petya", "Cool!"));
        commentService.addComment(new Comment(null, war_and_peace, "Vasya", "Много букв"));

        userService.saveUser(new User(null, "admin", "password", true,true,true,true));
        userService.saveUser(new User(null, "user", "password", true,true,true,true));

    }
}
