package ru.otus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@AllArgsConstructor
@Document
public class Book {
    @Id
    private String id;
    private String name;
    private List<Author> authorList;
    private Genre genre;
}
