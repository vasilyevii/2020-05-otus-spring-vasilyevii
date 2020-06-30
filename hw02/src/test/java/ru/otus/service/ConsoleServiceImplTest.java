package ru.otus.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ConsoleServiceImplTest {

    @Test
    void interString() {

        String data = "Hello, World!";
        InputStream stdin = System.in;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {

            System.setIn(new ByteArrayInputStream(data.getBytes()));
            System.setOut(new PrintStream(output));

            ConsoleService consoleService = new ConsoleServiceImpl();

            assertAll("console",
                    () -> assertThat(consoleService.interString("label")).isEqualTo(data),
                    () -> assertThat(output.toString().trim()).isEqualTo("label"));

        } finally {
            System.setIn(stdin);
            System.setOut(null);
        }
    }
}