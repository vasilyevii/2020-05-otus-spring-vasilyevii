package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.repositories.BookRepositoryJpa;
import ru.otus.services.BookService;

import java.util.*;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommands {

    @Autowired
    private BookService bookService;

    @ShellMethod(value = "Get all books", key = {"l", "list"})
    public void getAllBooks() {
        bookService.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get book by ID", key = {"g", "get"})
    public void getBook(int bookId) {
        System.out.println(bookService.findById(bookId));
    }

    @ShellMethod(value = "Add book", key = {"a", "add"})
    public void addBook(@ShellOption Integer bookId, @ShellOption String bookName,
                        @ShellOption(defaultValue = "") String author,
                        @ShellOption(defaultValue = "") String genre) {

        List<Author> authors = new ArrayList<>();
        Arrays.asList(author.split(";")).forEach(str -> {
            String authorName = str.trim();
            if (!authorName.isEmpty()) {
                authors.add(new Author(0, authorName));
            }
        });
        Book book = bookService.save(new Book(bookId, bookName, authors, new Genre(0, genre)));
        System.out.println("Book added! " + book);
    }

    @ShellMethod(value = "Delete book", key = {"d", "del"})
    public void deleteBook(int bookId) {
        bookService.deleteById(bookId);
    }

    @ShellMethod(value = "Add book comment", key = {"ac", "add comment"})
    public void addBookComment(@ShellOption Integer commentId, @ShellOption Integer bookId,
                               @ShellOption String userName, @ShellOption String text) {
        Optional<Book> book = bookService.findById(bookId);
        if (book.isPresent()) {
            Comment comment = bookService.addComment(new Comment(commentId, book.get(), userName, text));
            System.out.println("Book comment added! " + comment);
        } else {
            System.out.println("Can't find book with id ! " + bookId);
        }
    }

    @ShellMethod(value = "Get all book comments", key = {"lc", "list comments"})
    public void getAllBookComments(@ShellOption Integer bookId) {
        bookService.findAllCommentsByBookId(bookId).forEach(System.out::println);
    }

    @ShellMethod(value = "Delete all book comments", key = {"dc", "del comment"})
    public void deleteAllBookComments(@ShellOption Integer bookId) {
        bookService.deleteAllCommentsByBookId(bookId);
    }

}
