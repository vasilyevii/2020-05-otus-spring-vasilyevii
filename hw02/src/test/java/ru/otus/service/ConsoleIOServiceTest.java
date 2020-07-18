package ru.otus.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис ввода вывода ")
class ConsoleIOServiceTest {

    private static final String TEXT_TO_PRINT = "Ничто не истинно";
    private static final String TEXT_TO_READ = "Все дозволено";

    private IOService ioService;
    private ByteArrayOutputStream bos;

    @BeforeEach
    void setUp() {
        bos = new ByteArrayOutputStream();
        ioService = new ConsoleIOService(System.in, new PrintStream(bos));
    }

    @DisplayName("должен печатать \"" + TEXT_TO_PRINT + "\"")
    @SneakyThrows
    @Test
    void shouldPrintOnlyFirstCreedLine() {
        ioService.out(TEXT_TO_PRINT);
        Thread.sleep(1000);
        assertThat(bos.toString()).isEqualTo(TEXT_TO_PRINT + "\r\n");
    }

    @DisplayName("должен читать \"" + TEXT_TO_READ + "\"")
    @SneakyThrows
    @Test
    void shouldReadString () {

        InputStream stdin = System.in;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        System.setIn(new ByteArrayInputStream(TEXT_TO_READ.getBytes()));
        System.setOut(new PrintStream(output));

        IOService IOService = new ConsoleIOService(System.in, new PrintStream(bos));

        assertThat(IOService.readString()).isEqualTo(TEXT_TO_READ);

        System.setIn(stdin);
        System.setOut(null);

    }

}