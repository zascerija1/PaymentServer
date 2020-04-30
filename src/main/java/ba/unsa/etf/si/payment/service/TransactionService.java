package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.Merchant;
import ba.unsa.etf.si.payment.model.Transaction;
import ba.unsa.etf.si.payment.repository.TransactionRepository;
import ba.unsa.etf.si.payment.response.transactionResponse.TransactionDataResponse;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findByMerchantName(String merchantName){
        return transactionRepository.findAllByMerchant_MerchantNameAndPaymentStatus(merchantName,PaymentStatus.PAID);
    }

    public Transaction findByTransactionId(UUID id){
        Optional<Transaction> optTransaction= transactionRepository.findById(id);
        return optTransaction.orElse(null);
    }

    public  Transaction save(Transaction transaction){ return transactionRepository.save(transaction); }

    public void delete(UUID id){ transactionRepository.deleteById(id);}

    public List<TransactionDataResponse> findAllTransactionsByUserId(Long id){
        return  getTransactionData(transactionRepository
                .findByApplicationUser_IdAndPaymentStatus(id,PaymentStatus.PAID));

    }

    public List<TransactionDataResponse> findAllTransactionsByBankAccount(Long bankAccountId){

        return  getTransactionData(transactionRepository
                .findAllByBankAccount_IdAndPaymentStatus(bankAccountId,PaymentStatus.PAID));

    }

    public Transaction findByIdAndApplicationUser_Id(UUID transactionId, Long applicationUserId){
        Optional<Transaction> optTransaction= transactionRepository.findByIdAndApplicationUser_Id(transactionId,applicationUserId);
        return optTransaction.orElse(null);
    }

    public List<TransactionDataResponse> findAllTransactionsByUserAndDateBetween(Long userId, Date startDate, Date endDate){
        return getTransactionData(transactionRepository
                .findAllByApplicationUser_IdAndCreatedAtBetweenAndPaymentStatus(userId, startDate, endDate, PaymentStatus.PAID));

    }

    public List<TransactionDataResponse> findAllTransactionsByUserAndMerchantName(Long userId, String merchantName){
        return getTransactionData(transactionRepository
                .findAllByApplicationUser_IdAndMerchant_MerchantNameAndPaymentStatus(userId, merchantName,PaymentStatus.PAID));

    }

    public List<TransactionDataResponse> findAllTransactionsByUserIdAndService(Long userId, String service){
        return transactionRepository.findByApplicationUser_IdAndPaymentStatus(userId, PaymentStatus.PAID)
                .stream()
                .filter(transaction -> transaction.getService().toLowerCase().contains(service.toLowerCase()))
                .map(transaction -> {
                    BankAccount bankAcc=transaction.getBankAccount();
                    Merchant merchant=transaction.getMerchant();
                    return new TransactionDataResponse(transaction.getId(),
                            bankAcc.getCardNumber(),
                            merchant.getMerchantName(),
                            transaction.getCreatedAt(), transaction.getTotalPrice(),
                            transaction.getService());
                })
                .collect(Collectors.toList());
    }

    public List<TransactionDataResponse> findAllTransactionsByUserAndTotalPriceBetween(Long userId, Double minPrice, Double maxPrice){
        return getTransactionData(transactionRepository
                .findAllByApplicationUser_IdAndTotalPriceBetweenAndPaymentStatus(userId, minPrice, maxPrice,PaymentStatus.PAID));

    }

    private List<TransactionDataResponse> getTransactionData(List<Transaction> transactionList){
        return transactionList
                .stream()
                .map(transaction -> {
                    BankAccount bankAcc=transaction.getBankAccount();
                    Merchant merchant=transaction.getMerchant();
                    return new TransactionDataResponse(transaction.getId(),
                            bankAcc.getCardNumber(),
                            merchant.getMerchantName(),
                            transaction.getCreatedAt(), transaction.getTotalPrice(),
                            transaction.getService());
                })
                .collect(Collectors.toList());
    }

        public Transaction findByReceiptId(String receiptId){
        Optional<Transaction> optTransaction= transactionRepository.findByReceiptId(receiptId);
        return optTransaction.orElse(null);
    }
}
