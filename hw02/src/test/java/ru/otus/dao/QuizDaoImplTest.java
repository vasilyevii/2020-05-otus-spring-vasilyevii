package ru.otus.dao;

import org.junit.jupiter.api.Test;
import ru.otus.model.Question;
import ru.otus.model.Quiz;
import ru.otus.service.QuizException;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class QuizDaoImplTest {

    @Test
    void getQuiz() {

        QuizDao quizDao = new QuizDaoImpl("questions.csv", ",");
        Quiz quiz = quizDao.getQuiz();
        List<Question> questionList = quiz.getQuestions();

        assertAll("questions check",
                () -> assertThat(questionList).hasSize(1),
                () -> assertEquals(questionList.get(0).getText(), "1"),
                () -> assertEquals(questionList.get(0).getInputType(), "1"),
                () -> assertEquals(questionList.get(0).getAnswer(), "1"));
    }

    @Test
    void getQuizException() {

        QuizDao quizDao = new QuizDaoImpl("wrongfilename.csv", ",");

        assertThrows(QuizException.class,
                () -> quizDao.getQuiz());

    }
}