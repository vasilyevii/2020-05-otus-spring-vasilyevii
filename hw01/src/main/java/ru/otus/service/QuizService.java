package ru.otus.service;

public interface QuizService {

    void readQuestions();
    boolean hasNextQuestion();
    String askQuestion();

}
