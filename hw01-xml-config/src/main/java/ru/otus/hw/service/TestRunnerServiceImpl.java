package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.exceptions.QuestionReadException;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {
    private final IOService ioService;

    private final TestService testService;

    @Override
    public void run() {
        try {
            testService.executeTest();
        } catch (QuestionReadException e) {
            ioService.printLine("Couldn't get questions. cannot be continued");
        }

    }
}
