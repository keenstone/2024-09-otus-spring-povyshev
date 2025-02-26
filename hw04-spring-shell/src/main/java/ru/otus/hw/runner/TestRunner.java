package ru.otus.hw.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import ru.otus.hw.service.TestRunnerService;

//@Component
@RequiredArgsConstructor
public class TestRunner implements ApplicationRunner {
    private final TestRunnerService testRunnerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        testRunnerService.run();
    }
}
