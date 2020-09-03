package ru.otus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.CommentRepository;
import ru.otus.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final GenreRepository genreRepo;
    private final CommentRepository commentRepo;

    @Override
    @Transactional
    public Book saveBook(String bookName, String authorName, String genreName) {

        List<Author> authors = new ArrayList<>();
        Arrays.asList(authorName.split(";")).forEach(str -> {
            String authorTrimmedName = str.trim();
            if (!authorName.isEmpty()) {
                authors.add(new Author(null, authorTrimmedName));
            }
        });
        Genre genre = new Genre(null, genreName);
        Book book = new Book(null, bookName, authors, genre);

        List<Genre> genreByName = genreRepo.findByName(genre.getName());
        book.setGenre(genreByName.isEmpty() ? genreRepo.save(genre) : genreByName.get(0));

        List<Author> authorList = book.getAuthorList();
        for (int i = 0; i < authorList.size(); i++) {
            Author author = authorList.get(i);
            List<Author> authorByName = authorRepo.findByName(author.getName());
            authorList.set(i, authorByName.isEmpty() ? authorRepo.save(author) : authorByName.get(0));
        }

        return bookRepo.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findBookById(String bookId) {
        return bookRepo.findById(bookId);
    }

    @Override
    @Transactional
    public void deleteBookById(String bookId) {
        commentRepo.deleteAllByBook_Id(bookId);
        bookRepo.deleteById(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsByBookId(String bookId) {
        return commentRepo.findAllByBook_Id(bookId);
    }

    @Override
    @Transactional
    public void deleteAllCommentsByBookId(String bookId) {
        commentRepo.deleteAllByBook_Id(bookId);
    }
}
