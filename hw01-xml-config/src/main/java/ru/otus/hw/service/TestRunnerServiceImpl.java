package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    @Override
    public void run() {
        try{
            testService.executeTest();
        }catch (QuestionReadExceptin e){
            System.out.println("Couldn't get questions. cannot be continued")
        }

    }
}
