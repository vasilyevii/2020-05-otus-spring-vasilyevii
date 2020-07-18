package ru.otus.model;

import java.util.List;

public class Quiz {

    private final List<Question> questions;

    public Quiz(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
