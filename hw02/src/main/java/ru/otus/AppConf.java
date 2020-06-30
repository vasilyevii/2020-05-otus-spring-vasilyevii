package ru.otus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.service.ConsoleService;
import ru.otus.service.ConsoleServiceImpl;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class AppConf {

    @Bean
    public ConsoleService consoleService() {
        return new ConsoleServiceImpl();
    }

}
