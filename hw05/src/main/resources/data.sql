-- authors
insert into AUTHORS (NAME) values ('George Orwell');
insert into AUTHORS (NAME) values ('Stephane Faroult');
insert into AUTHORS (NAME) values ('Peter Robson');

-- genres
insert into GENRES (NAME) values ('Dystopian Fiction');
insert into GENRES (NAME) values ('Censorship & Politics');
insert into GENRES (NAME) values ('Database Storage & Design');

-- 1984
insert into BOOKS (ID, NAME) values (1, '1984');
insert into BOOKS_AUTHORS (BOOK_ID, AUTHOR) values (1, 'George Orwell');
insert into BOOKS_GENRES (BOOK_ID, GENRE) values (1, 'Dystopian Fiction');
insert into BOOKS_GENRES (BOOK_ID, GENRE) values (1, 'Censorship & Politics');

-- The Art of Sql
insert into BOOKS (id, name) values (2, 'The Art of Sql');
insert into BOOKS_AUTHORS (BOOK_ID, AUTHOR) values (2, 'Stephane Faroult');
insert into BOOKS_AUTHORS (BOOK_ID, AUTHOR) values (2, 'Peter Robson');
insert into BOOKS_GENRES (BOOK_ID, GENRE) values (2, 'Dystopian Fiction');
insert into BOOKS_GENRES (BOOK_ID, GENRE) values (2, 'Database Storage & Design');

-- Complete reference 11
insert into BOOKS (id, name) values (3, 'Complete reference 11');

-- Complete reference 12
insert into BOOKS (id, name) values (4, 'Complete reference 12');
insert into BOOKS_AUTHORS (BOOK_ID, AUTHOR) values (4, 'George Orwell');
insert into BOOKS_AUTHORS (BOOK_ID, AUTHOR) values (4, 'Stephane Faroult');
insert into BOOKS_GENRES (BOOK_ID, GENRE) values (4, 'Dystopian Fiction');
insert into BOOKS_GENRES (BOOK_ID, GENRE) values (4, 'Censorship & Politics');