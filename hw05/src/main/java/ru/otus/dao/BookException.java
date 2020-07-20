package ru.otus.dao;

public class BookException extends RuntimeException {

    public BookException(String message) {
        super(message);
    }

    public BookException(String message, Exception exception) {
        super(message, exception);
    }
}

