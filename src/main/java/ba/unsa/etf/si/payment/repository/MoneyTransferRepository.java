package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.MoneyTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, Long> {
    List<MoneyTransfer> findMoneyTransferByReceives(BankAccount bankAccount);
    List<MoneyTransfer> findMoneyTransferBySends(BankAccount bankAccount);
  //  List<BankAccount> findByReceives(Long id);
}