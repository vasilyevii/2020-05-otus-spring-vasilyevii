package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.QuizService;

@ShellComponent
@RequiredArgsConstructor
public class QuizCommands {

    @Autowired
    private QuizService quizService;

    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void login() {
        this.userName = quizService.login();
    }

    @ShellMethod(value = "Run quiz", key = {"r", "run"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public void runQuiz() {
        quizService.runQuiz();
    }

    @ShellMethod(value = "Summarize quiz", key = {"s", "sum"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public void summarize() {
        quizService.summarize();
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null? Availability.unavailable("Сначала залогиньтесь"): Availability.available();
    }

}
