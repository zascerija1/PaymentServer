package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.MoneyTransfer;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, Long> {
    List<MoneyTransfer> findMoneyTransferByReceivesAndPaymentStatus(BankAccount bankAccount, PaymentStatus paymentStatus);
    List<MoneyTransfer> findMoneyTransferBySendsAndPaymentStatus(BankAccount bankAccount, PaymentStatus paymentStatus);
  //  List<BankAccount> findByReceives(Long id);
}