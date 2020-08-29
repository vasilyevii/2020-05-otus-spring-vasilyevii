package ru.otus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document
public class Book {
    @Id
    private String id;
    private String name;
    private List<Author> authorList;
    private Genre genre;

    public Book(String name, List<Author> authorList, Genre genre) {
        this.name = name;
        this.authorList = authorList;
        this.genre = genre;
    }
}
