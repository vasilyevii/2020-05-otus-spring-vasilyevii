package ru.otus.dao;

public class QuizException extends RuntimeException {

    public QuizException(String message) {
        super(message);
    }

    public QuizException(String message, Exception exception) {
        super(message, exception);
    }
}

