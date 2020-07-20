package ru.otus.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.dao.BookDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;

@DisplayName("Сервис работы с книгами")
@SpringBootTest
class BookServiceImplTest {

    private static final long NEW_BOOK_ID = 5;
    private static final String NEW_BOOK_NAME = "new book name";
    private static final String FIRST_AUTHOR_NAME = "George Orwell";
    private static final String FIRST_GENRE_NAME = "Dystopian Fiction";

    @Autowired
    BookService bookService;

    @MockBean
    BookDao bookDao;

    @DisplayName("должен получать книгу по идентифцикатору с авторами и жанрами")
    @Test
    void shouldGetBookByIdWithNewAuthorsAndGenres() {
        Author author1 = new Author(FIRST_AUTHOR_NAME);
        Genre genre1 = new Genre(FIRST_GENRE_NAME);
        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        List<Genre> genres = new ArrayList<>();
        genres.add(genre1);
        Book expectedBook = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, new ArrayList<>(), new ArrayList<>());

        when(bookDao.getById(NEW_BOOK_ID)).thenReturn(Optional.of(expectedBook));
        when(bookDao.getBookAuthors(expectedBook)).thenReturn(authors);
        when(bookDao.getBookGenres(expectedBook)).thenReturn(genres);

        Book actualBook = bookService.getBookById(NEW_BOOK_ID);

        assertAll(
                () -> assertThat(actualBook.getId()).isEqualTo(NEW_BOOK_ID),
                () -> assertThat(actualBook.getName()).isEqualTo(NEW_BOOK_NAME),
                () -> assertThat(actualBook.getAuthors()).isEqualTo(authors),
                () -> assertThat(actualBook.getGenres()).isEqualTo(genres)
        );
    }
}