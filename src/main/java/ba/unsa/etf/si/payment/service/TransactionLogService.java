package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.Merchant;
import ba.unsa.etf.si.payment.model.TransactionLog;
import ba.unsa.etf.si.payment.repository.TransactionLogRepository;
import ba.unsa.etf.si.payment.response.transactionResponse.TransactionDataResponse;
import ba.unsa.etf.si.payment.response.transactionResponse.TransactionLogResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<TransactionLogResponse> getAllTransactionAttempts(UUID transactionId){

        return transactionLogRepository.findAllByTransaction_Id(transactionId)
                .stream()
                .map(transactionLog -> {
                    BankAccount bankAcc=transactionLog.getBankAccount();
                    String cardNumber=null;
                    if(bankAcc!=null)
                        cardNumber=bankAcc.getCardNumber();
                    return new TransactionLogResponse(transactionLog.getId(),
                            transactionLog.getCreatedAt(),
                            transactionLog.getPaymentStatus(),
                            cardNumber);
                })
                .collect(Collectors.toList());
    }
}
