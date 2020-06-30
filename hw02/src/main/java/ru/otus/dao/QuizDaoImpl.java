package ru.otus.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.model.Question;
import ru.otus.model.Quiz;
import ru.otus.service.QuizException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class QuizDaoImpl implements QuizDao {

    private final String questionsFileName;
    private final String commaDelimiter;

    public QuizDaoImpl(@Value("${quiz.questionsFileName}") String questionsFileName, @Value("${quiz.commaDelimiter}") String commaDelimiter) {
        this.questionsFileName = questionsFileName;
        this.commaDelimiter = commaDelimiter;
    }

    @Override
    public Quiz getQuiz() {

        List<Question> questions = new ArrayList<>();

        URL url = ClassLoader.getSystemResource(questionsFileName);
        if (url == null) {
            throw new QuizException("Questions file was not found: " + questionsFileName);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(url.openStream())))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] v = line.split(commaDelimiter);
                questions.add(new Question(v[0], v[1], v[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Quiz(questions);
    }

}
