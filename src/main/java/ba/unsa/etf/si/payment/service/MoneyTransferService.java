package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.MoneyTransfer;
import ba.unsa.etf.si.payment.repository.BankAccountUserRepository;
import ba.unsa.etf.si.payment.repository.MoneyTransferRepository;
import ba.unsa.etf.si.payment.response.MoneyTransferResponse;
import ba.unsa.etf.si.payment.response.PaymentResponse;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyTransferService {
    private final MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferService(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }
    public List<MoneyTransfer> findAllReceives(BankAccount bankAccount){
        return moneyTransferRepository.findMoneyTransferByReceives(bankAccount);
    }
    public List<MoneyTransfer> findAllSends(BankAccount bankAccount){
        return moneyTransferRepository.findMoneyTransferBySends(bankAccount);
    }
}
