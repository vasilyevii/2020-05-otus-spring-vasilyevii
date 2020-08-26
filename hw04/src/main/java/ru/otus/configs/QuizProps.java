package ru.otus.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "quiz")
@Setter @Getter
public class QuizProps {

    private String questionsFileName;
    private String commaDelimiter;
    private Locale locale;

}
