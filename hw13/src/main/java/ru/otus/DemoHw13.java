package ru.otus;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class DemoHw13 {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(DemoHw13.class);
        Console.main(args);
    }
}
