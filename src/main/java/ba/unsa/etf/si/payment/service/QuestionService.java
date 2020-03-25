package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.Question;
import ba.unsa.etf.si.payment.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Optional<Question> findById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public boolean existsById(Long questionId) {
        return questionRepository.existsById(questionId);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }
}
