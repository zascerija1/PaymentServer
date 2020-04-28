package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, UUID> {
    Integer countAllByTransaction_Id(UUID transactionId);
}
