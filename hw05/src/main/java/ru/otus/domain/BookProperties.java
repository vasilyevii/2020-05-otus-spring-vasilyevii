package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@ToString(callSuper=true, includeFieldNames=true)
@Data
abstract public class BookProperties {
    private final long id;
    private String name;
}
