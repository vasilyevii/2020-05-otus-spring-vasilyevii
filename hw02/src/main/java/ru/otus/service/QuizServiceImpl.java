package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuizDao;
import ru.otus.model.Question;
import ru.otus.model.Quiz;

import java.io.IOException;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;
    private final ConsoleService consoleService;

    public QuizServiceImpl(QuizDao quizDao, ConsoleService consoleService) {
        this.quizDao = quizDao;
        this.consoleService = consoleService;
    }

    @Override
    public Quiz getQuiz() {
        return quizDao.getQuiz();
    }

    @Override
    public void runQuiz(Quiz quiz) {
        for (Question question : quiz.getQuestions()) {
            String answer = consoleService.interString(question.getText() + "(" + question.getInputType() + "):");
            if (answer.trim().equals(question.getAnswer().trim())) {
                System.out.println("You're right!");
            } else {
                System.out.println("Try again!");
            }
        }
    }
}
