package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true, includeFieldNames=true)
@Data
public class Book {
    private final long id;
    private String name;
    private final List<Author> authors;
    private final List<Genre> genres;

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }
}
