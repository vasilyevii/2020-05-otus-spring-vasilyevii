package ru.otus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorRepositoryJpa;
import ru.otus.repositories.BookRepositoryJpa;
import ru.otus.repositories.CommentRepositoryJpa;
import ru.otus.repositories.GenreRepositoryJpa;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepo;
    private final GenreRepositoryJpa genreRepo;
    private final AuthorRepositoryJpa authorRepo;
    private final CommentRepositoryJpa commentRepo;

    @Override
    @Transactional
    public Book save(Book book) {
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
    public Optional<Book> findById(long id) {
        return bookRepo.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        return commentRepo.addComment(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsByBookId(long bookId) {
        return commentRepo.findAllCommentsByBookId(bookId);
    }

    @Override
    @Transactional
    public void deleteAllCommentsByBookId(long bookId) {
        commentRepo.deleteAllCommentsByBookId(bookId);
    }
}
