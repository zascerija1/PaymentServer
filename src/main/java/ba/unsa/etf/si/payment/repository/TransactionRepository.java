package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.Transaction;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByBankAccount_IdAndPaymentStatus(Long id, PaymentStatus paymentStatus);
    List<Transaction> findByApplicationUser_IdAndPaymentStatus(Long id, PaymentStatus paymentStatus);
    List<Transaction> findAllByMerchant_MerchantNameAndPaymentStatus(String merchantName, PaymentStatus paymentStatus);
    Optional<Transaction> findByIdAndApplicationUser_Id(UUID transactionId, Long applicationUserId);
    List<Transaction> findAllByApplicationUser_IdAndCreatedAtBetweenAndPaymentStatus(Long id, Date startDate, Date endDate, PaymentStatus paymentStatus);
    List<Transaction> findAllByApplicationUser_IdAndMerchant_MerchantNameAndPaymentStatus(Long id, String merchantName, PaymentStatus paymentStatus);
    List<Transaction> findAllByApplicationUser_IdAndTotalPriceBetweenAndPaymentStatus(Long id, Double minPrice, Double maxPrice, PaymentStatus paymentStatus);
    List<Transaction> findAllByApplicationUser_IdAndCreatedAtBetweenAndPaymentStatusAndBankAccount_Id(Long userId, Date startDate, Date endDate, PaymentStatus paymentStatus, Long bankAccountId);
    void deleteById(UUID id);
    Optional<Transaction> findByReceiptId(String receiptId);
}
