package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.services.BookService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommands {

    @Autowired
    BookService bookService;

    @ShellMethod(value = "Get all books", key = {"l", "list"})
    public void getAllBooks() {
        bookService.getAllBooks().forEach(System.out::println);
    }

    @ShellMethod(value = "Get book by ID", key = {"g", "get"})
    public void getBook(int bookId) {
        System.out.println(bookService.getBookById(bookId));
    }

    @ShellMethod(value = "Add book", key = {"a", "add"})
    public void addBook(@ShellOption(defaultValue = "0") int bookId, @ShellOption String bookName,
                        @ShellOption(defaultValue = "") String author, @ShellOption(defaultValue = "") String genre) {

        List<Author> authors = new ArrayList<>();
        Arrays.asList(author.split(";")).forEach(str -> {
            String authorName = str.trim();
            if (!authorName.isEmpty()) {
                authors.add(new Author(authorName));
            }
        });

        List<Genre> genres = new ArrayList<>();
        Arrays.asList(genre.split(";")).forEach(str -> {
            String genreName = str.trim();
            if (!genreName.isEmpty()) {
                genres.add(new Genre(genreName));
            }
        });

        long id = bookService.addBook(new Book(bookId, bookName, authors, genres));
        System.out.println("Book added! " + bookService.getBookById(id));
    }

    @ShellMethod(value = "Delete book", key = {"d", "del"})
    public void deleteBook(int bookId) {
        bookService.deleteBook(bookService.getBookById(bookId));
    }
}
