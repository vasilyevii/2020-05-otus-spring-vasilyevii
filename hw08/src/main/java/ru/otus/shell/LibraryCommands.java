package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.BookService;

import java.util.*;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommands {

    private final BookService bookService;

    @ShellMethod(value = "Get all books", key = {"l", "list"})
    public void getAllBooks() {
        bookService.findAllBooks().forEach(System.out::println);
    }

    @ShellMethod(value = "Get book by ID", key = {"g", "get"})
    public void getBookById(String bookId) {
        System.out.println(bookService.findBookById(bookId));
    }

    @ShellMethod(value = "Add book", key = {"a", "add"})
    public void addBook(@ShellOption String bookName,
                        @ShellOption(defaultValue = "") String authorName,
                        @ShellOption(defaultValue = "") String genreName) {

        Book book = bookService.saveBook(bookName, authorName, genreName);
        System.out.println("Book added! " + book);
    }

    @ShellMethod(value = "Delete book", key = {"d", "del"})
    public void deleteBook(@ShellOption String bookId) {
        bookService.deleteBookById(bookId);
    }

    @ShellMethod(value = "Add book comment", key = {"ac", "add comment"})
    public void addBookComment(@ShellOption String bookId, @ShellOption String userName, @ShellOption String text) {
        Optional<Book> book = bookService.findBookById(bookId);
        if (book.isPresent()) {
            Comment comment = bookService.addComment(new Comment(null, book.get(), userName, text));
            System.out.println("Book comment added! " + comment);
        } else {
            System.out.println("Can't find book with id ! " + bookId);
        }
    }

    @ShellMethod(value = "Get all book comments", key = {"lc", "list comments"})
    @Transactional
    public void getAllBookComments(@ShellOption String bookId) {
        bookService.findAllCommentsByBookId(bookId).forEach(System.out::println);
    }

    @ShellMethod(value = "Delete all book comments", key = {"dc", "del comment"})
    public void deleteAllBookComments(@ShellOption String bookId) {
        bookService.deleteAllCommentsByBookId(bookId);
    }

}
