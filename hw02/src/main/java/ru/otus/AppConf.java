package ru.otus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.service.IOService;
import ru.otus.service.ConsoleIOService;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class AppConf {

//    @Bean
//    public IOService consoleService() {
//        return new ConsoleIOService();
//    }

}
