package ru.otus.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("Сервис работы с опросом ")
class QuizServiceImplTest {

    private static final String RIGHT_OUTPUT_MSG = "You're right!";
    private static final String WRONG_OUTPUT_MSG = "Try again!";
    private static final String RIGHT_ANSWER = "right answer";
    private static final String WRONG_ANSWER = "wrong answer";

    @Mock
    private QuizDao quizDao;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("text", "inputType", RIGHT_ANSWER));
        quiz = new Quiz(questions);
    }

    @DisplayName("должен отвечать правильно")
    @Test
    void runQuizRightAnswer() {
        runQuiz(RIGHT_ANSWER, RIGHT_OUTPUT_MSG);
    }

    @DisplayName("должен отвечать не правильно")
    @Test
    void runQuizWrongAnswer() {
        runQuiz(WRONG_ANSWER, WRONG_OUTPUT_MSG);
    }

    @SneakyThrows
    void runQuiz(String answer, String outputMsg) {

        given(quizDao.getQuiz()).willReturn(quiz);

        InputStream stdin = System.in;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        System.setIn(new ByteArrayInputStream(answer.getBytes()));
        System.setOut(new PrintStream(output));

        IOService ioService = new ConsoleIOService(System.in, new PrintStream(output));
        QuizService quizService = new QuizServiceImpl(quizDao, ioService);
        quizService.runQuiz();

        assertThat(output.toString().trim()).isEqualTo("text(inputType):\r\n" + outputMsg);

        System.setIn(stdin);
        System.setOut(null);

    }

}