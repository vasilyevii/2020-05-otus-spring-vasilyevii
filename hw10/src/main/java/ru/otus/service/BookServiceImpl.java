package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.BookDto;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final GenreRepository genreRepo;
    private final CommentService commentService;

    @Override
    @Transactional
    public Book saveBook(Book book) {
        List<Author> authorList = book.getAuthorList();
        for (int i = 0; i < authorList.size(); i++) {
            Author author = authorList.get(i);
            List<Author> authorByName = authorRepo.findByName(author.getName());
            authorList.set(i, authorByName.isEmpty() ? authorRepo.save(author) : authorByName.get(0));
        }
        List<Genre> genreByName = genreRepo.findByName(book.getGenre().getName());
        book.setGenre(genreByName.isEmpty() ? genreRepo.save(book.getGenre()) : genreByName.get(0));
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
        commentService.deleteAllCommentsByBookId(bookId);
        bookRepo.deleteById(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public BookDto bookToBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                book.getAuthorList().stream()
                        .map(Author::getName)
                        .collect(Collectors.joining(";")),
                book.getGenre().getName()
        );
    }

    @Override
    public Book bookDtoToBook(BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getName(),
                Arrays.stream(bookDto.getAuthorList().split(";"))
                        .map(str -> new Author(null, str.trim()))
                        .collect(Collectors.toList()),
                new Genre(null, bookDto.getGenre()));
    }

}
