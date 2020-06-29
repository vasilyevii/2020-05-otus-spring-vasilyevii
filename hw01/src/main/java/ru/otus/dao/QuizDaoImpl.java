package ru.otus.dao;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizDaoImpl implements QuizDao {

    private final String questionsFileName;
    private final String commaDelimiter;

    public QuizDaoImpl(String questionsFileName, String commaDelimiter) {
        this.questionsFileName = questionsFileName;
        this.commaDelimiter = commaDelimiter;
    }

    @Override
    public List<List<String>> read() throws IOException {

        List<List<String>> questions = new ArrayList<>();

        URL url = ClassLoader.getSystemResource(questionsFileName);
        if (url == null) {
            throw new FileNotFoundException(questionsFileName);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(url.openStream())))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(commaDelimiter);
                questions.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }

}
