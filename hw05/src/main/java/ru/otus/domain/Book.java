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
    private Genre genre;

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

}
