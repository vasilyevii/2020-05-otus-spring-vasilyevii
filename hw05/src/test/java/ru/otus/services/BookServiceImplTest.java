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
    private static final long FIRST_AUTHOR_ID = 1;
    private static final String FIRST_AUTHOR_NAME = "George Orwell";
    private static final long FIRST_GENRE_ID = 1;
    private static final String FIRST_GENRE_NAME = "Dystopian Fiction";

    @Autowired
    private BookService bookService;

    @MockBean
    private BookDao bookDao;

    @DisplayName("должен получать книгу по идентифцикатору с авторами и жанрами")
    @Test
    void shouldGetBookByIdWithNewAuthorsAndGenres() {
        Author author1 = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        Genre genre1 = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        Book expectedBook = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, authors, genre1);

        when(bookDao.getById(NEW_BOOK_ID)).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.getBookById(NEW_BOOK_ID);

        assertAll(
                () -> assertThat(actualBook.getId()).isEqualTo(NEW_BOOK_ID),
                () -> assertThat(actualBook.getName()).isEqualTo(NEW_BOOK_NAME),
                () -> assertThat(actualBook.getAuthors()).isEqualTo(authors),
                () -> assertThat(actualBook.getGenre()).isEqualTo(genre1)
        );
    }
}