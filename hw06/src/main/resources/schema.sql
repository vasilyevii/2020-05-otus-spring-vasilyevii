drop table if exists authors CASCADE
;

drop table if exists books CASCADE
;

drop table if exists books_author_list CASCADE
;

drop table if exists comments CASCADE
;

drop table if exists genres CASCADE
;

create table authors
(
    id   bigint generated by default as identity,
    name varchar(255) not null,
    primary key (id)
)
;

create table books
(
    id       bigint generated by default as identity,
    name     varchar(255) not null,
    genre_id bigint,
    primary key (id)
)
;

create table books_author_list
(
    book_id        bigint not null,
    author_list_id bigint not null
)
;

create table comments
(
    id        bigint generated by default as identity,
    text      varchar(255) not null,
    user_name varchar(255) not null,
    book_id   bigint,
    primary key (id)
)
;

create table genres
(
    id   bigint generated by default as identity,
    name varchar(255) not null,
    primary key (id)
)
;

alter table authors
    add constraint UK_9mhkwvnfaarcalo4noabrin5j unique (name)
;

alter table books
    add constraint UK_eh6bfj824qyn40pii29i90bll unique (name)
;

alter table genres
    add constraint UK_pe1a9woik1k97l87cieguyhh4 unique (name)
;

alter table books
    add constraint FK9hsvoalyniowgt8fbufidqj3x
        foreign key (genre_id)
            references genres
;

alter table books_author_list
    add constraint FKlkhmvt8tq9xjbmntxbcq4jl9
        foreign key (author_list_id)
            references authors
;

alter table books_author_list
    add constraint FK4tbva6kuwwccb4pjeyu1nyne2
        foreign key (book_id)
            references books
;

alter table comments
    add constraint FK1ey8gegnanvybix5a025vepf4
        foreign key (book_id)
            references books
;
