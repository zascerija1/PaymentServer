package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> find(String cvc, String cardNumber) {
        return bankAccountRepository.findByCvcAndCardNumber(cvc, cardNumber);
    }

    public List<BankAccount> findByCardNumber(String cardNumber) {
        return bankAccountRepository.findByCardNumber(cardNumber);
    }

    public BankAccount save(BankAccount bankAccount) {
        return  bankAccountRepository.save(bankAccount);
    }
}
