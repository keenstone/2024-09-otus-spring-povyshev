package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {
    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private LocalizedIOService localizedIOService;

    @Mock
    private QuestionProcessService questionProcessService;

    @InjectMocks
    private TestServiceImpl testServiceImpl;

    @DisplayName("Should print questions")
    @Test
    void printQuestionsFromFile(){
        var answer11 = new Answer("first answer",false);
        var answer12 = new Answer("second answer",false);
        var answer13 = new Answer("third answer",true);
        var answers1 = Arrays.asList(answer11,answer12,answer13);
        var answer21 = new Answer("first answer again",false);
        var answer22 = new Answer("second answer again",true);
        var answer23 = new Answer("third answer again",false);
        var answers2 = Arrays.asList(answer21,answer22,answer23);
        var question1 = new Question("Choose the right answer",answers1);
        var question2 = new Question("Choose the right answer again",answers2);
        var testQuestion = Arrays.asList(question1,question2);
        var student = new Student("first name","last name");
        InOrder inOrder = inOrder(questionDao,questionProcessService);

        given(questionDao.findAll()).willReturn(testQuestion);

        testServiceImpl.executeTestFor(student);
        verify(questionDao,times(1)).findAll();

        inOrder.verify(questionDao).findAll();
        inOrder.verify(questionProcessService).processQuestion(question1);
        inOrder.verify(questionProcessService).processQuestion(question2);

        //verify(questionProcessService,times(1)).processQuestion();

        //видимо тут надо выводить в строку то, что было выведено в IOService и сравнивать


    }
}
