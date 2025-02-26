package ru.otus.hw.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.service.TestRunnerService;


@ShellComponent(value = "Test app")
public class TestCommand {
    private final TestRunnerService testRunnerService;




    public TestCommand(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;

    }

    @ShellMethod(value = "Test student method", key = {"test","t"})
    @ShellMethodAvailability("availabilityCheck")
    public void test() {
        testRunnerService.run();
    }

    public Availability availabilityCheck() {
        return Availability.available();
    }
}
