package ru.otus.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.configs.QuizProps;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Сервис ввода вывода ")
@SpringBootTest
class ConsoleIOServiceTest {

    private static final String TEXT_TO_PRINT_WITHOUT_ARGS = "enter_you_name";
    private static final String TEXT_TO_READ_WITHOUT_ARGS_RU = "Введите ваше имя:";
    private static final String TEXT_TO_READ_WITHOUT_ARGS_EN = "Enter your name:";

    private static final String TEXT_TO_PRINT_WITH_ARGS = "hello_user";
    private static final String TEXT_TO_ARG_RU = "Человек";
    private static final String TEXT_TO_READ_WITH_ARGS_RU = "Привет, " + TEXT_TO_ARG_RU + "!";
    private static final String TEXT_TO_ARG_EN = "Man";
    private static final String TEXT_TO_READ_WITH_ARGS_EN = "Hello, " + TEXT_TO_ARG_EN + "!";

    private static final String TEXT_TO_PRINT = "Ничто не истинно";
    private static final String TEXT_TO_READ = "Все дозволено";

    @Autowired
    private MessageSource messageSource;

    @MockBean
    private QuizProps props;

    @DisplayName("должен печатать сообщение без аргументов на русском")
    @SneakyThrows
    @Test
    void shouldPrintAMessageWithoutArgumentsInRussian() {
        when(props.getLocale()).thenReturn(new Locale("ru_RU"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOService ioService = new ConsoleIOService(messageSource, props, System.in, new PrintStream(bos));
        ioService.out(TEXT_TO_PRINT_WITHOUT_ARGS);
        Thread.sleep(100);
        assertThat(bos.toString()).isEqualTo(TEXT_TO_READ_WITHOUT_ARGS_RU + "\r\n");
    }

    @DisplayName("должен печатать сообщение без аргументов на английском")
    @SneakyThrows
    @Test
    void shouldPrintAMessageWithoutArgumentsInEnglish() {
        when(props.getLocale()).thenReturn(new Locale("en"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOService ioService = new ConsoleIOService(messageSource, props, System.in, new PrintStream(bos));
        ioService.out(TEXT_TO_PRINT_WITHOUT_ARGS);
        Thread.sleep(100);
        assertThat(bos.toString()).isEqualTo(TEXT_TO_READ_WITHOUT_ARGS_EN + "\r\n");
    }

    @DisplayName("должен печатать сообщение c аргументами на русском")
    @SneakyThrows
    @Test
    void shouldPrintAMessageWithArgumentsInRussian() {
        when(props.getLocale()).thenReturn(new Locale("ru_RU"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOService ioService = new ConsoleIOService(messageSource, props, System.in, new PrintStream(bos));
        ioService.out(TEXT_TO_PRINT_WITH_ARGS, new String[]{TEXT_TO_ARG_RU});
        Thread.sleep(100);
        assertThat(bos.toString()).isEqualTo(TEXT_TO_READ_WITH_ARGS_RU + "\r\n");
    }

    @DisplayName("должен печатать сообщение c аргументами на анлийском")
    @SneakyThrows
    @Test
    void shouldPrintAMessageWithArgumentsInEnglish() {
        when(props.getLocale()).thenReturn(new Locale("en"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOService ioService = new ConsoleIOService(messageSource, props, System.in, new PrintStream(bos));
        ioService.out(TEXT_TO_PRINT_WITH_ARGS, new String[]{TEXT_TO_ARG_EN});
        Thread.sleep(100);
        assertThat(bos.toString()).isEqualTo(TEXT_TO_READ_WITH_ARGS_EN + "\r\n");
    }


    @DisplayName("должен печатать \"" + TEXT_TO_PRINT + "\"")
    @SneakyThrows
    @Test
    void shouldPrintOnlyFirstCreedLine() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOService ioService = new ConsoleIOService(messageSource, props, System.in, new PrintStream(bos));
        ioService.outText(TEXT_TO_PRINT);
        Thread.sleep(100);
        assertThat(bos.toString()).isEqualTo(TEXT_TO_PRINT + "\r\n");
    }

    @DisplayName("должен читать \"" + TEXT_TO_READ + "\"")
    @SneakyThrows
    @Test
    void shouldReadString () {
        InputStream stdin = System.in;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream(TEXT_TO_READ.getBytes()));
        System.setOut(new PrintStream(output));
        IOService IOService = new ConsoleIOService(messageSource, props, System.in, new PrintStream(bos));
        assertThat(IOService.readString()).isEqualTo(TEXT_TO_READ);
        System.setIn(stdin);
        System.setOut(null);
    }

}