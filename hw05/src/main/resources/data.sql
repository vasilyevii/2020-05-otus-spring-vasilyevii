-- authors
INSERT INTO AUTHORS (ID, NAME) VALUES (1, 'George Orwell');
INSERT INTO AUTHORS (ID, NAME) VALUES (2, 'Stephane Faroult');
INSERT INTO AUTHORS (ID, NAME) VALUES (3, 'Peter Robson');

-- genres
INSERT INTO GENRES (ID, NAME) VALUES (1, 'Dystopian Fiction');

-- 1984
INSERT INTO BOOKS (ID, NAME, GENRE_ID) VALUES (1, '1984', 1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (1, 1);

-- The Art of Sql
INSERT INTO BOOKS (ID, NAME, GENRE_ID) VALUES (2, 'The Art of Sql', 1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (2, 2);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (2, 3);

-- Complete reference 11
INSERT INTO BOOKS (ID, NAME, GENRE_ID) VALUES (3, 'Complete reference 11', 1);

-- Complete reference 12
INSERT INTO BOOKS (ID, NAME, GENRE_ID) VALUES (4, 'Complete reference 12', 1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (4, 1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (4, 2);