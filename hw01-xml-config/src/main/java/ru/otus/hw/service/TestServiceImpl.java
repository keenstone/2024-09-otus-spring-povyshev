package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions =  questionDao.findAll();
        questions.forEach(this::processQuestion);
    }

    private void processQuestion(Question question) {
        ioService.printLine("");
        ioService.printLine(question.text());
        question.answers().forEach(this::processAnswer);
    }

    private void processAnswer(Answer answer) {
        ioService.printLine(" - " + answer.text());
    }
}
