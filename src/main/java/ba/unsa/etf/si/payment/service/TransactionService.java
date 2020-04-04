package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.model.Merchant;
import ba.unsa.etf.si.payment.model.Transaction;
import ba.unsa.etf.si.payment.repository.BankAccountUserRepository;
import ba.unsa.etf.si.payment.repository.TransactionRepository;
import ba.unsa.etf.si.payment.response.TransactionDataResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findByMerchantName(String merchantName){
        return transactionRepository.findAllByMerchant_MerchantNameAndProcessed(merchantName,true);
    }

    public Transaction findByTransactionId(Long id){
        Optional<Transaction> optTransaction= transactionRepository.findById(id);
        return optTransaction.orElse(null);
    }

    public  Transaction save(Transaction transaction){ return transactionRepository.save(transaction); }

    public void delete(Long id){ transactionRepository.deleteById(id);}

    public List<TransactionDataResponse> findAllTransactionsByUserId(Long id){
        return  transactionRepository.findByApplicationUser_IdAndProcessed(id,true)
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

        return  transactionRepository.findAllByBankAccount_IdAndProcessed(bankAccountId,true)
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

    public Transaction findByIdAndApplicationUser_Id(Long transactionId, Long applicationUserId){
        Optional<Transaction> optTransaction= transactionRepository.findByIdAndApplicationUser_Id(transactionId,applicationUserId);
        return optTransaction.orElse(null);
    }

    public List<TransactionDataResponse> findAllTransactionsBetween(Date startDate, Date endDate){
        return transactionRepository.findAllByCreatedAtBetweenAndProcessed(startDate, endDate,true)
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

    public List<TransactionDataResponse> findAllTransactionsByUserAndMerchantName(Long userId, String merchantName){
        return transactionRepository.findAllByApplicationUser_IdAndMerchant_MerchantNameAndProcessed(userId, merchantName,true)
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

    public List<TransactionDataResponse> findAllTransactionsByUserIdAndService(Long userId, String service){
        return transactionRepository.findByApplicationUser_IdAndProcessed(userId, true)
                .stream()
                .filter(transaction -> transaction.getService().toLowerCase().contains(service.toLowerCase()))
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
