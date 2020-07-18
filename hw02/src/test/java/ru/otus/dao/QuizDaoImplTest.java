package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Question;
import ru.otus.model.Quiz;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Дао для работы с опросом ")
class QuizDaoImplTest {

    private static final String QUESTIONS_FILE = "questions.csv";
    private static final String WRONG_QUESTIONS_FILE = "wrongfilename.csv";
    private static final String QUESTIONS_FILE_WITH_WRONG_QUESTION = "questions_with_wrong_question.csv";


    @Test
    @DisplayName("должен получать объект опроса с одним вопросом ")
    void shouldReturnQuizObjectWithOneQuestion() {

        QuizDao quizDao = new QuizDaoImpl(QUESTIONS_FILE, ",");
        Quiz quiz = quizDao.getQuiz();
        List<Question> questionList = quiz.getQuestions();

        assertAll("questions check",
                () -> assertThat(questionList).hasSize(1),
                () -> assertEquals(questionList.get(0).getText(), "1"),
                () -> assertEquals(questionList.get(0).getInputType(), "1"),
                () -> assertEquals(questionList.get(0).getAnswer(), "1"));
    }

    @Test
    @DisplayName("должен выкидывать исключение, если файл с вопросами не найден")
    void shouldThrowExceptionIfQuestionsFilesWasNotFound() {
        QuizDao quizDao = new QuizDaoImpl(WRONG_QUESTIONS_FILE, ",");
        assertThrows(QuizException.class, quizDao::getQuiz);
    }

    @Test
    @DisplayName("должен выкидывать исключение, если вопрос из файла содержит менее трех полей")
    void shouldThrowExceptionIfQuestionHaveLessThanThreeFields() {
        QuizDao quizDao = new QuizDaoImpl(QUESTIONS_FILE_WITH_WRONG_QUESTION, ",");
        assertThrows(QuizException.class, quizDao::getQuiz);
    }

}