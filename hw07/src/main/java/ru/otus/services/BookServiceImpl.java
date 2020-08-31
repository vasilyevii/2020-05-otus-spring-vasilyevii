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
    private final GenreRepository genreRepo;
    private final AuthorRepository authorRepo;
    private final CommentRepository commentRepo;

    @Override
    @Transactional
    public Book saveBook(long bookId, String bookName, String authorName, String genreName) {

        List<Author> authors = new ArrayList<>();
        Arrays.asList(authorName.split(";")).forEach(str -> {
            String authorTrimmedName = str.trim();
            if (!authorName.isEmpty()) {
                authors.add(new Author(0, authorTrimmedName));
            }
        });
        Book book = new Book(bookId, bookName, authors, new Genre(0, genreName));

        List<Genre> genreByName = genreRepo.findByName(book.getGenre().getName());
        if (!genreByName.isEmpty()) {
            book.setGenre(genreByName.get(0));
        }

        List<Author> authorList = book.getAuthorList();
        for (int i = 0; i < authorList.size(); i++) {
            List<Author> authorByName = authorRepo.findByName(authorList.get(i).getName());
            if (!authorByName.isEmpty()) {
                authorList.set(i, authorByName.get(0));
            }
        }

        return bookRepo.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findBookById(long id) {
        return bookRepo.findById(id);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        commentRepo.deleteAllByBook_Id(id);
        bookRepo.deleteById(id);
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
    public List<Comment> findAllCommentsByBookId(long bookId) {
        return commentRepo.findAllByBook_Id(bookId);
    }

    @Override
    @Transactional
    public void deleteAllCommentsByBookId(long bookId) {
        commentRepo.deleteAllByBook_Id(bookId);
    }
}
