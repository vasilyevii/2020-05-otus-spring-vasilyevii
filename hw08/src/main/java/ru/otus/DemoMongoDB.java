package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class DemoMongoDB {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoMongoDB.class);
    }
}
