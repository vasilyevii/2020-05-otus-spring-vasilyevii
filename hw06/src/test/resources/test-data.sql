INSERT INTO AUTHORS (NAME) VALUES ('author1'), ('author2');
INSERT INTO GENRES (NAME) VALUES ('genre1'), ('genre2');
INSERT INTO BOOKS (NAME, GENRE_ID) VALUES ('for update', 1), ('for delete', 1), ('for read', 1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (1, 1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (2, 1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (3, 1);
INSERT INTO COMMENTS (TEXT, USER_NAME, BOOK_ID) VALUES ('text1', 'user name', 3);
INSERT INTO COMMENTS (TEXT, USER_NAME, BOOK_ID) VALUES ('text1', 'user name', 3);