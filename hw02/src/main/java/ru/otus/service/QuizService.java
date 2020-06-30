package ru.otus.service;

import ru.otus.model.Quiz;

public interface QuizService {

    Quiz getQuiz();
    void runQuiz(Quiz quiz);

}
