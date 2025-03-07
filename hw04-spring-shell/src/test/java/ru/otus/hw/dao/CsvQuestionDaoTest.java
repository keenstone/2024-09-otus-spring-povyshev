package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {CsvQuestionDao.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class CsvQuestionDaoTest {

    @MockitoBean
    private TestFileNameProvider testFileNameProvider;

    @Autowired
    private QuestionDao questionDao;

    @DisplayName("Extract Questions and Answers from CSV file")
    @Test
    void test(){
        var answers1 = Arrays.asList(new Answer("first answer",false),new Answer("second answer",false),new Answer("third answer",true));
        var answers2 = Arrays.asList(new Answer("first answer again",false),new Answer("second answer again",true),new Answer("third answer again",false));
        var testQuestion1 = new Question("Choose the right answer",answers1);
        var testQuestion2 = new Question("Choose the right answer again",answers2);

        given(testFileNameProvider.getTestFileName()).willReturn("testquestions.csv");
        var questions = questionDao.findAll();
        assertThat(questions).satisfiesExactly(firstAnswer -> assertThat(firstAnswer).isEqualTo(testQuestion1),
                secondAnswer -> assertThat(secondAnswer).isEqualTo(testQuestion2));
    }
}
