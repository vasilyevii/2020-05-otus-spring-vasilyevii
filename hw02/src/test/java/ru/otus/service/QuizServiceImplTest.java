package ru.otus.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
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
    private static final String USER_NAME = "Ivan Ivanov";
    private static final String WELCOME_STRING = "Enter your name: \r\nHello " + USER_NAME;
    private static final String GOODBYE_STRING = "Enter your name: \r\nGoodbye stranger";
    private static final String SUMMARIZE_STRING = "1 right answers out of 1";

    @Mock
    private QuizDao quizDao;

    static private Quiz quiz;
    private InputStream stdin;
    private ByteArrayOutputStream output;

    @BeforeAll
    static void setUpAll() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("text", "inputType", RIGHT_ANSWER));
        quiz = new Quiz(questions);
    }

    @BeforeEach
    void setUpEach() {
        stdin = System.in;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDownEach() {
        System.setIn(stdin);
        System.setOut(null);
    }

    @Test
    @DisplayName("должен приветствовать пользователя, который ввел свое имя")
    void shouldLoginUserWhoEnteredHisName() {
        System.setIn(new ByteArrayInputStream(USER_NAME.getBytes()));
        IOService ioService = new ConsoleIOService(System.in, new PrintStream(output));
        QuizService quizService = new QuizServiceImpl(quizDao, ioService);
        quizService.login();
        assertThat(output.toString().trim()).isEqualTo(WELCOME_STRING);
}

    @Test
    @DisplayName("должен прощаться с пользователем, который не ввел свое имя")
    void shouldGoodbyeUserWhoDidNotEnterHisName() {
        System.setIn(new ByteArrayInputStream(" ".getBytes()));
        IOService ioService = new ConsoleIOService(System.in, new PrintStream(output));
        QuizService quizService = new QuizServiceImpl(quizDao, ioService);
        quizService.login();
        assertThat(output.toString().trim()).isEqualTo(GOODBYE_STRING);
    }

    @Test
    @DisplayName("должен выводить результат теста")
    void shouldPrintQuizSummarize() {
        given(quizDao.getQuiz()).willReturn(quiz);
        System.setIn(new ByteArrayInputStream(RIGHT_ANSWER.getBytes()));
        IOService ioService = new ConsoleIOService(System.in, new PrintStream(output));
        QuizService quizService = new QuizServiceImpl(quizDao, ioService);
        quizService.runQuiz();
        quizService.summarize();
        assertThat(output.toString().trim()).isEqualTo("text(inputType):\r\n" + RIGHT_OUTPUT_MSG + "\r\n" + SUMMARIZE_STRING);
    }

    @Test
    @DisplayName("должен отвечать правильно")
    void runQuizRightAnswer() {
        runQuiz(RIGHT_ANSWER, RIGHT_OUTPUT_MSG);
    }

    @Test
    @DisplayName("должен отвечать не правильно")
    void runQuizWrongAnswer() {
        runQuiz(WRONG_ANSWER, WRONG_OUTPUT_MSG);
    }

    @SneakyThrows
    void runQuiz(String answer, String outputMsg) {
        given(quizDao.getQuiz()).willReturn(quiz);
        System.setIn(new ByteArrayInputStream(answer.getBytes()));
        IOService ioService = new ConsoleIOService(System.in, new PrintStream(output));
        QuizService quizService = new QuizServiceImpl(quizDao, ioService);
        quizService.runQuiz();
        assertThat(output.toString().trim()).isEqualTo("text(inputType):\r\n" + outputMsg);
    }


}