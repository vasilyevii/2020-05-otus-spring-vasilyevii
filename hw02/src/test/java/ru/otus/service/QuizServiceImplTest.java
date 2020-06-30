package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.dao.QuizDao;
import ru.otus.model.Question;
import ru.otus.model.Quiz;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    private QuizDao quizDao;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("text", "inputType", "right answer"));
        quiz = new Quiz(questions);
    }

    @Test
    void getQuiz() {
        QuizService quizService = new QuizServiceImpl(quizDao, new ConsoleServiceImpl());
        given(quizDao.getQuiz()).willReturn(quiz);
        assertThat(quizService.getQuiz()).isEqualTo(quiz);
    }

    @Test
    void runQuizRightAnswer() {
        runQuiz("right answer", "You're right!");
    }

    @Test
    void runQuizWrongAnswer() {
        runQuiz("wrong answer", "Try again!");
    }

    void runQuiz(String answer, String outputMsg) {

        InputStream stdin = System.in;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {

            System.setIn(new ByteArrayInputStream(answer.getBytes()));
            System.setOut(new PrintStream(output));

            QuizService quizService = new QuizServiceImpl(quizDao, new ConsoleServiceImpl());
            quizService.runQuiz(quiz);

            assertThat(output.toString().trim()).isEqualTo("text(inputType):\r\n" + outputMsg);

        } finally {
            System.setIn(stdin);
            System.setOut(null);
        }
    }

}