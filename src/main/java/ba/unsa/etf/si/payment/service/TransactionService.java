package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.Transaction;
import ba.unsa.etf.si.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findByMerchantName(String merchantName){
        return transactionRepository.findAllByMerchant_MerchantName(merchantName);
    }

    public  Transaction save(Transaction transaction){ return transactionRepository.save(transaction); }

    public void delete(Long id){ transactionRepository.deleteById(id);}
}
