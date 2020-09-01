package ru.otus.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DisplayName("Дао работы с книгами")
@JdbcTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class})
class BookDaoImplTest {

    private static final long NEW_BOOK_ID = 5;
    private static final String NEW_BOOK_NAME = "new book name";
    private static final long FIRST_EXISTING_BOOK_ID = 1;
    private static final long SECOND_EXISTING_BOOK_ID = 2;
    private static final long THIRD_EXISTING_BOOK_ID = 3;
    private static final String THIRD_EXISTING_BOOK_NAME = "Complete reference 11";
    private static final long FOURTH_EXISTING_BOOK_ID = 4;

    private static final long FIRST_AUTHOR_ID = 1;
    private static final String FIRST_AUTHOR_NAME = "George Orwell";
    private static final long SECOND_AUTHOR_ID = 2;
    private static final String SECOND_AUTHOR_NAME = "Stephane Faroult";
    private static final long FOURTH_AUTHOR_ID = 4;
    private static final String FOURTH_AUTHOR_NAME = "King";
    private static final long FIRST_GENRE_ID = 1;
    private static final String FIRST_GENRE_NAME = "Dystopian Fiction";
    private static final long SECOND_GENRE_ID = 2;
    private static final String SECOND_GENRE_NAME = "Horror";

    @Autowired
    private BookDao bookDao;

    @DisplayName("должен добавлять новую книгу")
    @Test
    void shouldInsertNewBook() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME));
        Genre genre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        Book expectedBook = new Book(0, NEW_BOOK_NAME, authors, genre);
        long id = bookDao.insert(expectedBook);
        Optional<Book> actualBook = bookDao.getById(id);

        assertAll(
                () -> assertThat(actualBook).isNotEmpty(),
                () -> assertThat(actualBook.get().getId()).isEqualTo(NEW_BOOK_ID),
                () -> assertThat(actualBook.get().getName()).isEqualTo(NEW_BOOK_NAME),
                () -> assertThat(actualBook.get().getAuthors()).isEqualTo(authors),
                () -> assertThat(actualBook.get().getGenre()).isEqualTo(genre)
        );
    }

    @DisplayName("должен обновлять существующую книгу")
    @Test
    void shouldUpdateExistingBook() {
        Book book = bookDao.getById(FIRST_EXISTING_BOOK_ID).get();
        book.setName(NEW_BOOK_NAME);
        book.getAuthors().clear();
        Author secondAuthor = new Author(0, FOURTH_AUTHOR_NAME);
        book.addAuthor(secondAuthor);
        Genre newGenre = new Genre(0, SECOND_GENRE_NAME);
        book.setGenre(newGenre);
        bookDao.update(book);
        Optional<Book> actualBook = bookDao.getById(FIRST_EXISTING_BOOK_ID);

        assertAll(
                () -> assertThat(actualBook).isNotEmpty(),
                () -> assertThat(actualBook.get().getId()).isEqualTo(FIRST_EXISTING_BOOK_ID),
                () -> assertThat(actualBook.get().getName()).isEqualTo(NEW_BOOK_NAME),
                () -> assertThat(actualBook.get().getAuthors()).hasSize(1).contains(new Author(FOURTH_AUTHOR_ID, FOURTH_AUTHOR_NAME)),
                () -> assertThat(actualBook.get().getGenre()).isEqualTo(new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME))
        );
    }

    @DisplayName("должен удалять существующую книгу")
    @Test
    void shouldDeleteExistingBook() {
        Book book = bookDao.getById(SECOND_EXISTING_BOOK_ID).get();
        bookDao.delete(book);

        assertThat(bookDao.getById(SECOND_EXISTING_BOOK_ID)).isEmpty();
    }

    @DisplayName("должен получать существующую книгу по идентификатору")
    @Test
    void shouldGetExistingBookById() {
        Optional<Book> book = bookDao.getById(THIRD_EXISTING_BOOK_ID);

        assertAll(
                () -> assertThat(book).isNotEmpty(),
                () -> assertThat(book.get().getId()).isEqualTo(THIRD_EXISTING_BOOK_ID),
                () -> assertThat(book.get().getName()).isEqualTo(THIRD_EXISTING_BOOK_NAME),
                () -> assertThat(book.get().getAuthors()).isEmpty()
        );
    }

    @DisplayName("должен получать список всех авторов книги")
    @Test
    void shouldGetAListOfAllAuthorsOfTheBook() {
        Author author1 = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        Author author2 = new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME);
        Book book = bookDao.getById(FOURTH_EXISTING_BOOK_ID).get();
        List<Author> bookAuthors = book.getAuthors();

        assertThat(bookAuthors)
                .isNotEmpty()
                .hasSize(2)
                .contains(author1, author2);
    }
}