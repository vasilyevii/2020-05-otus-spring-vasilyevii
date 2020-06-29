package ru.otus.service;

import ru.otus.dao.QuizDao;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class QuizServiceImpl implements QuizService {

    private static final Scanner scanner = new Scanner(System.in);
    private final QuizDao quizDao;
    private List<List<String>> questions;
    private int cursor;

    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public void readQuestions() throws QuizException {
        try {
            questions = quizDao.read();
        } catch (IOException ex) {
            throw new QuizException("Questions file was not found", ex);
        }
        cursor = 0;
    }

    @Override
    public boolean hasNextQuestion() {
        return questions.size() >= cursor + 1;
    }

    @Override
    public String askQuestion() {
        List<String> question = questions.get(cursor);
        cursor++;
        System.out.println(question.get(0) + " (" + question.get(1) + "):");
        return scanner.nextLine();
    }

}
