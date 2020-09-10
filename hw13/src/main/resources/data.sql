INSERT INTO AUTHORS (NAME) VALUES ('Pushkin'), ('Lermontov'), ('Tolstoy');
INSERT INTO GENRES (NAME) VALUES ('Classic'), ('Horror');
INSERT INTO BOOKS (NAME, GENRE_ID) VALUES ('Boris Godunov', 1), ('A Hero of Our Time', 1), ('War and Peace', 2);
INSERT INTO BOOKS_AUTHOR_LIST (BOOK_ID, AUTHOR_LIST_ID) VALUES (1, 1), (2, 2), (3, 3);
INSERT INTO COMMENTS (TEXT, USER_NAME, BOOK_ID) VALUES ('Cool!', 'Петя', 3), ('Много буков', 'Вася', 3);

INSERT INTO USERS (NAME, PASSWORD, ROLE, IS_ACCOUNT_NON_EXPIRED, IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED)
    VALUES ('admin', 'password', 'ROLE_ADMIN', true, true, true, true),
           ('user1', 'password', 'ROLE_USER', true, true, true, true),
           ('user2', 'password', 'ROLE_USER', true, true, true, true),
           ('manager', 'password', 'ROLE_MANAGER', true, true, true, true);
INSERT INTO USERS_GENRE_LIST (USER_ID, GENRE_LIST_ID) VALUES (2, 1), (3, 2);
----------------------

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'user1'),
(2, 1, 'user2'),
(3, 1, 'admin');

INSERT INTO acl_class (id, class) VALUES
(1, 'ru.otus.model.Book'),
(2, 'ru.otus.model.Genre');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 3, 0), -- book 'Boris Godunov'
(2, 1, 2, NULL, 3, 0), -- book 'A Hero of Our Time'
(3, 1, 3, NULL, 3, 0), -- book 'War and Peace'
(4, 2, 1, NULL, 3, 0), -- genre 'Classic'
(5, 2, 2, NULL, 3, 0); -- genre 'Horror'

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 4, 1, 1, 1, 1, 1, 1), -- genre 'Classic' / user1 / read
(2, 5, 1, 2, 1, 1, 1, 1); -- genre 'Classic' / user2 / read