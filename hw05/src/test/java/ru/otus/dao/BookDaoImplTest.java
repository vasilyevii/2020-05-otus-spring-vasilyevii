package ru.otus.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DisplayName("Дао работы с книгами")
@SpringBootTest
class BookDaoImplTest {

    private static final long NEW_BOOK_ID = 5;
    private static final String NEW_BOOK_NAME = "new book name";
    private static final long FIRST_EXISTING_BOOK_ID = 1;
    private static final long SECOND_EXISTING_BOOK_ID = 2;
    private static final long THIRD_EXISTING_BOOK_ID = 3;
    private static final String THIRD_EXISTING_BOOK_NAME = "Complete reference 11";
    private static final long FOURTH_EXISTING_BOOK_ID = 4;
    private static final String FIRST_AUTHOR_NAME = "George Orwell";
    private static final String SECOND_AUTHOR_NAME = "Stephane Faroult";
    private static final String FIRST_GENRE_NAME = "Dystopian Fiction";
    private static final String SECOND_GENRE_NAME = "Censorship & Politics";

    @Autowired
    BookDao bookDao;

    @DisplayName("должен добавлять новую книгу")
    @Test
    void shouldInsertNewBook() {
        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Book expectedBook = new Book(0, NEW_BOOK_NAME, authors, genres);
        long id = bookDao.insert(expectedBook);
        Optional<Book> actualBook = bookDao.getById(id);

        assertAll(
                () -> assertThat(actualBook).isNotEmpty(),
                () -> assertThat(actualBook.get().getId()).isEqualTo(NEW_BOOK_ID),
                () -> assertThat(actualBook.get().getName()).isEqualTo(NEW_BOOK_NAME),
                () -> assertThat(actualBook.get().getAuthors()).isEmpty(),
                () -> assertThat(actualBook.get().getGenres()).isEmpty()
        );
    }

    @DisplayName("должен обновлять существующую книгу")
    @Test
    void shouldUpdateExistingBook() {
        Book book = bookDao.getById(FIRST_EXISTING_BOOK_ID).get();
        book.setName(NEW_BOOK_NAME);
        bookDao.update(book);
        book = bookDao.getById(FIRST_EXISTING_BOOK_ID).get();

        assertThat(book.getName()).isEqualTo(NEW_BOOK_NAME);
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
                () -> assertThat(book.get().getAuthors()).isEmpty(),
                () -> assertThat(book.get().getGenres()).isEmpty()
        );
    }

    @DisplayName("должен добавлять автора книги")
    @Test
    void shouldAddTheAuthorOfTheBook() {
        Author author1 = new Author(FIRST_AUTHOR_NAME);
        Author author2 = new Author(SECOND_AUTHOR_NAME);
        Book book = bookDao.getById(THIRD_EXISTING_BOOK_ID).get();
        bookDao.insertBookAuthorRelation(THIRD_EXISTING_BOOK_ID, FIRST_AUTHOR_NAME);
        bookDao.insertBookAuthorRelation(THIRD_EXISTING_BOOK_ID, SECOND_AUTHOR_NAME);
        List<Author> bookAuthors = bookDao.getBookAuthors(book);

        assertThat(bookAuthors)
                .isNotEmpty()
                .hasSize(2)
                .contains(author1, author2);
    }

    @DisplayName("должен удалять всех авторов книги")
    @Test
    void shouldDeleteAllAuthorsOfTheBook() {
        Book book = bookDao.getById(FIRST_EXISTING_BOOK_ID).get();
        bookDao.deleteBookAuthorRelations(book);
        List<Author> bookAuthors = bookDao.getBookAuthors(book);

        assertThat(bookAuthors).isEmpty();
    }

    @DisplayName("должен получать список всех авторов книги")
    @Test
    void shouldGetAListOfAllAuthorsOfTheBook() {
        Author author1 = new Author(FIRST_AUTHOR_NAME);
        Author author2 = new Author(SECOND_AUTHOR_NAME);
        Book book = bookDao.getById(FOURTH_EXISTING_BOOK_ID).get();
        List<Author> bookAuthors = bookDao.getBookAuthors(book);

        assertThat(bookAuthors)
                .isNotEmpty()
                .hasSize(2)
                .contains(author1, author2);
    }

    @DisplayName("должен добавлять жанр книги")
    @Test
    void shouldAddTheGenreOfTheBook() {
        Genre genre1 = new Genre(FIRST_GENRE_NAME);
        Genre genre2 = new Genre(SECOND_GENRE_NAME);
        Book book = bookDao.getById(THIRD_EXISTING_BOOK_ID).get();
        bookDao.insertBookGenreRelation(THIRD_EXISTING_BOOK_ID, FIRST_GENRE_NAME);
        bookDao.insertBookGenreRelation(THIRD_EXISTING_BOOK_ID, SECOND_GENRE_NAME);
        List<Genre> bookGenres = bookDao.getBookGenres(book);

        assertThat(bookGenres)
                .isNotEmpty()
                .hasSize(2)
                .contains(genre1, genre2);
    }

    @DisplayName("должен удалять все жанры книги")
    @Test
    void shouldDeleteAllGenresOfTheBook() {
        Book book = bookDao.getById(FIRST_EXISTING_BOOK_ID).get();
        bookDao.deleteBookGenreRelations(book);
        List<Genre> bookGenres = bookDao.getBookGenres(book);

        assertThat(bookGenres).isEmpty();
    }

    @DisplayName("должен получать список всех жанров книги")
    @Test
    void shouldGetAListOfAllGenresOfTheBook() {
        Genre genre1 = new Genre(FIRST_GENRE_NAME);
        Genre genre2 = new Genre(SECOND_GENRE_NAME);
        Book book = bookDao.getById(FOURTH_EXISTING_BOOK_ID).get();
        List<Genre> bookGenres = bookDao.getBookGenres(book);

        assertThat(bookGenres)
                .isNotEmpty()
                .hasSize(2)
                .contains(genre1, genre2);
    }
}