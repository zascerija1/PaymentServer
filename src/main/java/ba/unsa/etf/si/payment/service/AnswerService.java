package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.Answer;
import ba.unsa.etf.si.payment.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> find(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public Answer save(Answer answer) {
        return  answerRepository.save(answer);
    }
}
