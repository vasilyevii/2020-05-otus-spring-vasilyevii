package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.configs.QuizProps;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {

//    @Autowired
//    @Qualifier("reloadableResourceBundleMessageSource")
    private final MessageSource messageSource;

    private final QuizProps props;

    private final PrintStream out;
    private final Scanner sc;

    public ConsoleIOService(MessageSource messageSource, QuizProps props,
                            @Value("#{ T(java.lang.System).in}") InputStream in,
                            @Value("#{ T(java.lang.System).out}") PrintStream out) {

        this.messageSource = messageSource;
        this.props = props;
        this.out = out;
        this.sc = new Scanner(in);
    }

    @Override
    public void out(String message) {
        out.println(messageSource.getMessage(message, null, props.getLocale()));
    }

    @Override
    public void out(String message, Object[] args) {
        out.println(messageSource.getMessage(message, args, props.getLocale()));
    }

    @Override
    public void outText(String message) {
        out.println(message);
    }

    @Override
    public String readString() {
        return sc.nextLine();
    }

}
