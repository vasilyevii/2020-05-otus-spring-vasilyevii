package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.BookService;

import java.util.*;

@ShellComponent
@RequiredArgsConstructor
@Transactional
public class LibraryCommands {

    private final BookService bookService;

    @ShellMethod(value = "Get all books", key = {"l", "list"})
    public void getAllBooks() {
        bookService.findAll().forEach(System.out::println);
    }

    @ShellMethod(value = "Get book by ID", key = {"g", "get"})
    public void getBookById(long bookId) {
        System.out.println(bookService.findById(bookId));
    }

    @ShellMethod(value = "Add book", key = {"a", "add"})
    public void addBook(@ShellOption long bookId, @ShellOption String bookName,
                        @ShellOption(defaultValue = "") String authorName,
                        @ShellOption(defaultValue = "") String genreName) {

        List<Author> authors = new ArrayList<>();
        Arrays.asList(authorName.split(";")).forEach(str -> {
            String authorTrimmedName = str.trim();
            if (!authorName.isEmpty()) {
                authors.add(new Author(0, authorTrimmedName));
            }
        });
        Book book = bookService.save(new Book(bookId, bookName, authors, new Genre(0, genreName)));
        System.out.println("Book added! " + book);
    }

    @ShellMethod(value = "Delete book", key = {"d", "del"})
    public void deleteBook(long bookId) {
        bookService.deleteById(bookId);
    }

    @ShellMethod(value = "Add book comment", key = {"ac", "add comment"})
    public void addBookComment(@ShellOption long commentId, @ShellOption long bookId,
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
    public void getAllBookComments(@ShellOption long bookId) {
        bookService.findAllCommentsByBookId(bookId).forEach(System.out::println);
    }

    @ShellMethod(value = "Delete all book comments", key = {"dc", "del comment"})
    public void deleteAllBookComments(@ShellOption long bookId) {
        bookService.deleteAllCommentsByBookId(bookId);
    }

}
