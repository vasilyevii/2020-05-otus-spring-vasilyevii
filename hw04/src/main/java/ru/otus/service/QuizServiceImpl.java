package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.QuizDao;
import ru.otus.model.Question;
import ru.otus.model.Quiz;

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
    public String login() {
        ioService.out("enter_you_name");
        String userName = ioService.readString().trim();
        if (!userName.equals("")) {
            ioService.out("hello_user", new String[]{userName});
        } else {
            ioService.out("goodbye_stranger");
        }
        return userName;
    }

    @Override
    public void runQuiz() {
        Quiz quiz = quizDao.getQuiz();
        List<Question> questions = quiz.getQuestions();
        numberOfQuestions = questions.size();
        for (Question question : questions) {
            ioService.outText(question.getText() + " (" + question.getInputType() + "):");
            String answer = ioService.readString();
            if (answer.trim().equals(question.getAnswer().trim())) {
                rightAnswers++;
                ioService.out("you_are_right");
            } else {
                ioService.out("try_again");
            }
        }
    }

    @Override
    public void summarize() {
        ioService.out("summarize_quiz", new Object[]{rightAnswers, numberOfQuestions});
    }

}
