package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.service.QuizService;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConf.class);
        QuizService quizService = context.getBean(QuizService.class);
        quizService.runQuiz();

    }
}
