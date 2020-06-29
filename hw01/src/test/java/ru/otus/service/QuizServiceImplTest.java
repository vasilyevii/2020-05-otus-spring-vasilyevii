package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.otus.dao.QuizDao;

class QuizServiceImplTest {

    @Mock
    private QuizDao quizDao;

    private QuizService quizService;

    @BeforeEach
    void setUp() {
        quizService = new QuizServiceImpl(quizDao);
    }

    @Test
    void readQuestions() {
        quizService.readQuestions();
    }

    @Test
    void hasNextQuestion() {
        quizService.hasNextQuestion();
    }

    @Test
    void askQuestion() {
        quizService.askQuestion();
    }

}