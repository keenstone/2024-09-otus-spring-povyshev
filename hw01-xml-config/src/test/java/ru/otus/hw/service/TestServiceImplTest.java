package ru.otus.hw.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.Arrays;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {
    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testServiceImpl;

    @DisplayName("Should print questions")
    @Test
    void printQuestionsFromFile(){
        var answers = Arrays.asList(new Answer("first answer",false),new Answer("second answer",false),new Answer("third answer",true));
        var testQuestion = Arrays.asList(new Question("Choose the right answer",answers));
        given(questionDao.findAll()).willReturn(testQuestion);
        testServiceImpl.executeTest();
        verify(questionDao,times(1)).findAll();
        //видимо тут надо выводить в строку то, что было выведено в IOService и сравнивать


    }
}
