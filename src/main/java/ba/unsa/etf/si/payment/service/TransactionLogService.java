package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.TransactionLog;
import ba.unsa.etf.si.payment.repository.TransactionLogRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionLogService {
    private final TransactionLogRepository transactionLogRepository;

    public TransactionLogService(TransactionLogRepository transactionLogRepository) {
        this.transactionLogRepository = transactionLogRepository;
    }

    public Integer getNumberOfAttempts(UUID transactionId){
        return transactionLogRepository.countAllByTransaction_Id(transactionId);
    }

    public TransactionLog save(TransactionLog transactionLog){
        return transactionLogRepository.save(transactionLog);
    }
}
