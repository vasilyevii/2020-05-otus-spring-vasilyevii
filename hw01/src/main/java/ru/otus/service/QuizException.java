package ru.otus.service;

import org.springframework.lang.Nullable;

public class QuizException extends RuntimeException {
    public QuizException(String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
