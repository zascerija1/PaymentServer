package ba.unsa.etf.si.payment.controller;

import ba.unsa.etf.si.payment.model.Question;
import ba.unsa.etf.si.payment.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return questionService.findAll();
    }

    @GetMapping("/question")
    public String getQuestion() {
        return "hellloo";
    }


    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionService.save(question);
    }
}