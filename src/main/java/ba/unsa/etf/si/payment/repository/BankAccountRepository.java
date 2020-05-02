package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByCvcAndCardNumber(String cvc, String cardNumber);
    List<BankAccount> findByCardNumber(String cardNumber);

}