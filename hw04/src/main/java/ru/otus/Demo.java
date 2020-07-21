package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.configs.QuizProps;
import ru.otus.service.QuizService;

@SpringBootApplication
@EnableConfigurationProperties(QuizProps.class)
public class Demo {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Demo.class, args);
//        QuizService quizService = context.getBean(QuizService.class);
//        if (quizService.login()) {
//            quizService.runQuiz();
//            quizService.summarize();
//        }
    }
}
