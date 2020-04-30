package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.MoneyTransfer;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, UUID> {
    List<MoneyTransfer> findMoneyTransferByReceivesAndPaymentStatus(BankAccount bankAccount, PaymentStatus paymentStatus);
    List<MoneyTransfer> findMoneyTransferBySendsAndPaymentStatus(BankAccount bankAccount, PaymentStatus paymentStatus);
  //  List<BankAccount> findByReceives(Long id);
}