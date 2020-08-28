package ru.otus.services;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    GenreRepository genreRepo;

    @Autowired
    AuthorRepository authorRepo;

    @Autowired
    CommentRepository commentRepo;

    @Override
    @Transactional
    public Book saveBook(Book book) {
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
