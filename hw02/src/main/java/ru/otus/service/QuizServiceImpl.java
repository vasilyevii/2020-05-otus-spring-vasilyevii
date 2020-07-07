package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.QuizDao;
import ru.otus.model.Question;
import ru.otus.model.Quiz;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;
    private final IOService ioService;

    public QuizServiceImpl(QuizDao quizDao, IOService ioService) {
        this.quizDao = quizDao;
        this.ioService = ioService;
    }

    @Override
    public void runQuiz() {
        Quiz quiz = quizDao.getQuiz();
        for (Question question : quiz.getQuestions()) {
            ioService.out(question.getText() + "(" + question.getInputType() + "):");
            String answer = ioService.readString();
            if (answer.trim().equals(question.getAnswer().trim())) {
                ioService.out("You're right!");
            } else {
                ioService.out("Try again!");
            }
        }
    }
}
