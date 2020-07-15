package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.QuizDao;
import ru.otus.model.Question;
import ru.otus.model.Quiz;

import java.text.Format;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;
    private final IOService ioService;
    private int rightAnswers = 0;
    private int numberOfQuestions = 0;

    public QuizServiceImpl(QuizDao quizDao, IOService ioService) {
        this.quizDao = quizDao;
        this.ioService = ioService;
    }

    @Override
    public boolean login() {
        ioService.out("Enter your name: ");
        String userName = ioService.readString();
        if (!userName.trim().equals("")) {
            ioService.out("Hello " + userName);
            return true;
        } else {
            ioService.out("Goodbye stranger");
            return false;
        }
    }

    @Override
    public void runQuiz() {

        Quiz quiz = quizDao.getQuiz();
        List<Question> questions = quiz.getQuestions();
        numberOfQuestions = questions.size();
        for (Question question : questions) {
            ioService.out(question.getText() + "(" + question.getInputType() + "):");
            String answer = ioService.readString();
            if (answer.trim().equals(question.getAnswer().trim())) {
                rightAnswers++;
                ioService.out("You're right!");
            } else {
                ioService.out("Try again!");
            }
        }
    }

    @Override
    public void summarize() {
        ioService.out(rightAnswers + " right answers out of " + numberOfQuestions);
    }

}
