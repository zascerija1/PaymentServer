package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}