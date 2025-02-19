package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestionProcessServiceImpl implements  QuestionProcessService {

    private final LocalizedIOService ioService;

    @Override
    public boolean processQuestion(Question question) {
        printQuestion(question);
        question.answers().forEach(this::printAnswer);
        return getResult(question);
    }

    private boolean getResult(Question question) {
        var selectedAnswer = ioService.readIntForRangeWithPrompt(1,question.answers().size(),
                String.format("Enter correct answer. Print digit in range 1-%s",question.answers().size()),
                "Couldn't process such answer. Try again.");
        return checkAnswer(question, selectedAnswer);
    }

    private boolean checkAnswer(Question question, int selectedAnswer) {
        var answerVariants = getAnswersVariants(question);
        return answerVariants.containsKey(selectedAnswer) && answerVariants.get(selectedAnswer).isCorrect();
    }

    private Map<Integer,Answer> getAnswersVariants(Question question) {
        Map<Integer,Answer> answerVariants = new HashMap<>();
        for (int index = 0; index < question.answers().size();index++) {
            answerVariants.put(index + 1,question.answers().get(index));
        }
         return answerVariants;
    }

    private void printAnswer(Answer answer) {
        ioService.printLine(" - " + answer.text());
    }

    private void printQuestion(Question question) {
        ioService.printLine("");
        ioService.printLine(question.text());


    }

}
