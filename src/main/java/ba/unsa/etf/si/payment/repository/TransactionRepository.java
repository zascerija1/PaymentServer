package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByBankAccount_IdAndProcessed(Long id,Boolean processed);
    List<Transaction> findByApplicationUser_IdAndProcessed(Long id,Boolean processed);
    List<Transaction> findAllByMerchant_MerchantNameAndProcessed(String merchantName,Boolean processed);
    Optional<Transaction> findByIdAndApplicationUser_Id(Long transactionId, Long applicationUserId);
    List<Transaction> findAllByCreatedAtBetween(Date startDate, Date endDate);
    List<Transaction> findAllByApplicationUser_IdAndMerchant_MerchantName(Long id, String merchantName);
}
