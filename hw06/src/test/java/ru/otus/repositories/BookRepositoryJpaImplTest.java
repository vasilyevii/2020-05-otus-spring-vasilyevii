package ru.otus.repositories;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookRepositoryJpaImpl.class)
class BookRepositoryJpaImplTest {

    private static final long NEW_BOOK_ID = 4L;
    private static final String NEW_BOOK_NAME = "for create";
    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_NAME = "for update done";
    private static final long SECOND_BOOK_ID = 2L;
    private static final long THIRD_BOOK_ID = 3L;
    private static final String THIRD_BOOK_NAME = "for read";
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long SECOND_AUTHOR_ID = 2L;
    private static final String THIRD_AUTHOR_NAME = "author3";
    private static final long SECOND_GENRE_ID = 2L;
    private static final String THIRD_GENRE_NAME = "genre3";
    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;
    private static final String COMMENT_USER_NAME = "Vasya";
    private static final String COMMENT_TEXT = "GK/AM";
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;

    @Autowired
    private BookRepositoryJpaImpl repo;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен находить все книги")
    @Test
    void shouldFindAllBooks() {
        List<Book> books = repo.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getAuthorList() != null && s.getAuthorList().size() > 0)
                .allMatch(s -> s.getGenre() != null);
    }

    @DisplayName(" должен создавать новую книгу")
    @Test
    void shouldCreateNewBook() {
        Author author = new Author(0, THIRD_AUTHOR_NAME);
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(author);
        Genre genre = new Genre(0, THIRD_GENRE_NAME);
        Book expectedBook = new Book(0, NEW_BOOK_NAME, authors, genre);

        Book actualBook = repo.save(expectedBook);

        assertThat(actualBook)
                .hasFieldOrPropertyWithValue("id", NEW_BOOK_ID)
                .hasFieldOrPropertyWithValue("name", NEW_BOOK_NAME)
                .hasFieldOrPropertyWithValue("genre", genre);
        assertThat(actualBook.getAuthorList()).hasSize(1).contains(author);
    }

    @DisplayName(" должен изменять существующую книгу")
    @Test
    void shouldUpdateExistingBook() {
        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        expectedBook.setName(FIRST_BOOK_NAME);
        Author author1 = em.find(Author.class, FIRST_AUTHOR_ID);
        Author author2 = em.find(Author.class, SECOND_AUTHOR_ID);
        expectedBook.getAuthorList().add(author2);
        Genre genre2 = em.find(Genre.class, SECOND_GENRE_ID);
        expectedBook.setGenre(genre2);
        Book actualBook = repo.save(expectedBook);

        assertThat(actualBook)
                .hasFieldOrPropertyWithValue("id", FIRST_BOOK_ID)
                .hasFieldOrPropertyWithValue("name", FIRST_BOOK_NAME)
                .hasFieldOrPropertyWithValue("genre", genre2);
        assertThat(actualBook.getAuthorList()).hasSize(2).contains(author1, author2);
    }

    @DisplayName(" должен находить книгу по ID")
    @Test
    void shouldFindBookById() {
        Optional<Book> actualBook = repo.findById(THIRD_BOOK_ID);
        assertThat(actualBook).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("name", THIRD_BOOK_NAME);
    }

    @DisplayName(" должен удалять книгу по ID")
    @Test
    void shouldDeleteBookById() {
        Book book = em.find(Book.class, SECOND_BOOK_ID);
        assertThat(book).isNotNull();
        repo.deleteById(SECOND_BOOK_ID);
        em.clear();
        book = em.find(Book.class, SECOND_BOOK_ID);
        assertThat(book).isNull();
    }

    @DisplayName(" должен находить книгу по имени")
    @Test
    void shouldFindBookByName() {
        List<Book> actualBook = repo.findByName(THIRD_BOOK_NAME);
        assertThat(actualBook).hasSize(1)
                .first().hasFieldOrPropertyWithValue("id", THIRD_BOOK_ID);
    }

    @DisplayName(" должен добавлять комментарий")
    @Test
    void shouldAddComment() {
        Book book = em.find(Book.class, THIRD_BOOK_ID);
        Comment comment1 = repo.addComment(new Comment(0, book, COMMENT_USER_NAME, COMMENT_TEXT));
        assertThat(comment1)
                .hasFieldOrPropertyWithValue("book", book)
                .hasFieldOrPropertyWithValue("userName", COMMENT_USER_NAME)
                .hasFieldOrPropertyWithValue("text", COMMENT_TEXT);
    }

    @DisplayName(" должен находить все комментарии книги по ID")
    @Test
    void shouldFindAllCommentsByBookId() {
        List<Comment> comments = repo.findAllCommentsByBookId(THIRD_BOOK_ID);
        assertThat(comments).hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(s -> !s.getText().equals(""))
                .allMatch(s -> s.getBook() != null)
                .allMatch(s -> !s.getUserName().equals(""));
    }

    @DisplayName(" должен удалять все комментарии книги по ID")
    @Test
    void deleteAllCommentsByBookId() {
        repo.deleteAllCommentsByBookId(THIRD_BOOK_ID);
        List<Comment> comments = repo.findAllCommentsByBookId(THIRD_BOOK_ID);
        assertThat(comments).isEmpty();
    }
}