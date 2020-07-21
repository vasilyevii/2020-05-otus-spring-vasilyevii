package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.configs.QuizProps;
import ru.otus.model.Question;
import ru.otus.model.Quiz;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuizDaoImpl implements QuizDao {

    private final QuizProps quizProps;

    @Override
    public Quiz getQuiz() {

        List<Question> questions = new ArrayList<>();

        URL url = ClassLoader.getSystemResource(quizProps.getQuestionsFileName());
        if (url == null) {
            throw new QuizException("Questions file was not found: " + quizProps.getQuestionsFileName());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(url.openStream())))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] v = line.split(quizProps.getCommaDelimiter());
                if (v.length < 3) {
                    throw new QuizException("Question have less than three fields: " + quizProps.getQuestionsFileName());
                }
                questions.add(new Question(v[0], v[1], v[2]));
            }
        } catch (IOException e) {
            throw new QuizException("Questions file cannot be read: " + quizProps.getQuestionsFileName(), e);
        }

        return new Quiz(questions);
    }

}
