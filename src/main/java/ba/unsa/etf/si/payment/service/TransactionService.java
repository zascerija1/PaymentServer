package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.model.Merchant;
import ba.unsa.etf.si.payment.model.Transaction;
import ba.unsa.etf.si.payment.repository.BankAccountUserRepository;
import ba.unsa.etf.si.payment.repository.TransactionRepository;
import ba.unsa.etf.si.payment.response.TransactionDataResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<TransactionDataResponse> findAllTransactionsByUserId(Long id){
        return  transactionRepository.findByApplicationUser_Id(id)
                .stream()
                .map(transaction -> {
                    BankAccount bankAcc=transaction.getBankAccount();
                    Merchant merchant=transaction.getMerchant();
                    return new TransactionDataResponse(bankAcc.getCardNumber(),
                            merchant.getMerchantName(),
                            transaction.getCreatedAt(), transaction.getTotalPrice(),
                            transaction.getService());
                })
                .collect(Collectors.toList());
    }

    public List<TransactionDataResponse> findAllTransactionsByBankAccount(Long bankAccountId){

        return  transactionRepository.findAllByBankAccount_Id(bankAccountId)
                .stream()
                .map(transaction -> {
                    BankAccount bankAcc=transaction.getBankAccount();
                    Merchant merchant=transaction.getMerchant();
                    return new TransactionDataResponse(bankAcc.getCardNumber(),
                            merchant.getMerchantName(),
                            transaction.getCreatedAt(), transaction.getTotalPrice(),
                            transaction.getService());
                })
                .collect(Collectors.toList());
    }


}
