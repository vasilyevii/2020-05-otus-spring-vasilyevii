package ru.otus.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString(callSuper=true, includeFieldNames=true)
@Data
public class Author {
    private final String name;
}

