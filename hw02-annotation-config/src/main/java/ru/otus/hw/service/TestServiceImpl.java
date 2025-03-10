package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    private final QuestionProcessService questionProcessService;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = questionProcessService.processQuestion(question); // я бы добавил объекты questionresult
            // и возвращал его. его передавал в апплай резалт. тут смешение мне кажется ансверов
            // как вариантов ответов и того, что сделал студент. если завтра захотим сделать несколько правильных ответов
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
