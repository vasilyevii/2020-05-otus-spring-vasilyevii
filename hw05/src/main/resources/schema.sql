DROP TABLE IF EXISTS BOOKS;
CREATE TABLE IF NOT EXISTS BOOKS (ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255), GENRE_ID BIGINT);

-- AUTHORS
DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS (ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));
CREATE INDEX IDX_NAME ON AUTHORS(NAME);

DROP TABLE IF EXISTS BOOKS_AUTHORS;
CREATE TABLE BOOKS_AUTHORS (BOOK_ID BIGINT, AUTHOR_ID BIGINT, PRIMARY KEY (BOOK_ID, AUTHOR_ID));
CREATE INDEX IDX_AUTHOR_ID ON BOOKS_AUTHORS(AUTHOR_ID);

-- GENRES
DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES (ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));


alter table books
    add constraint FK9hsvoalyniowgt8fbufidqj3x
        foreign key (genre_id)
            references genres;

alter table books_authors
    add constraint FK3qua08pjd1ca1fe2x5cgohuu5
        foreign key (author_id)
            references authors;

alter table books_authors
    add constraint FK1b933slgixbjdslgwu888m34v
        foreign key (book_id)
            references books;