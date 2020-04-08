package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByBankAccount_IdAndProcessed(Long id,Boolean processed);
    List<Transaction> findByApplicationUser_IdAndProcessed(Long id,Boolean processed);
    List<Transaction> findAllByMerchant_MerchantNameAndProcessed(String merchantName,Boolean processed);
    Optional<Transaction> findByIdAndApplicationUser_Id(UUID transactionId, Long applicationUserId);
    List<Transaction> findAllByApplicationUser_IdAndCreatedAtBetweenAndProcessed(Long id,Date startDate, Date endDate, Boolean processed);
    List<Transaction> findAllByApplicationUser_IdAndMerchant_MerchantNameAndProcessed(Long id, String merchantName, Boolean processed);
    List<Transaction> findAllByApplicationUser_IdAndTotalPriceBetweenAndProcessed(Long id, Double minPrice, Double maxPrice, Boolean processed);
    void deleteById(UUID id);
}
