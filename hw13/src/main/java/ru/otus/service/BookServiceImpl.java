package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.BookDto;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.security.AclService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final AclService aclService;

    @Override
    @Transactional
    public Book saveBook(Book book) {
        final boolean newBook = book.getId() == 0;
        List<Author> authorList = book.getAuthorList();
        for (int i = 0; i < authorList.size(); i++) {
            authorList.set(i, authorService.getAuthor(authorList.get(i)));
        }
        book.setGenre(genreService.getGenre(book.getGenre()));
        bookRepo.save(book);
        if (newBook) {
            aclService.addAclPermissions(book, Arrays.asList(BasePermission.READ, BasePermission.WRITE));
        }
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findBookById(long bookId) {
        return bookRepo.findById(bookId);
    }

    @Override
    @Transactional
    public void deleteBookById(long bookId) {
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
                        .map(str -> new Author(0, str.trim()))
                        .collect(Collectors.toList()),
                new Genre(0, bookDto.getGenre()));
    }
}
