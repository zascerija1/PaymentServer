package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.repository.BankAccountUserRepository;
import ba.unsa.etf.si.payment.response.BankAccountDataResponse;
import ba.unsa.etf.si.payment.response.PaymentResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountUserService {

    private final BankAccountUserRepository bankAccountUserRepository;


    public BankAccountUserService(BankAccountUserRepository bankAccountUserRepository) {
        this.bankAccountUserRepository = bankAccountUserRepository;
    }

    public BankAccountUser save(BankAccountUser bankAccountUser) {
        return bankAccountUserRepository.save(bankAccountUser);
    }

    public List<BankAccountDataResponse> findBankAccounts(Long userId) {
        return bankAccountUserRepository.findAllByApplicationUser_Id(userId)
                .stream()
                .map(bankAccountUser -> {
                    BankAccount bankAccount = bankAccountUser.getBankAccount();
                    //Very important!!!
                    //we send bankAccountUserId, because we want do delete a row in that table
                    //not a bank account which  actually doesn't belong to our system
                    return new BankAccountDataResponse(bankAccountUser.getId(), bankAccount.getAccountOwner(),
                            bankAccount.getBank().getBankName(), bankAccount.getExpiryDate(), bankAccount.getCardNumber());
                })
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        bankAccountUserRepository.deleteById(id);
    }

    public boolean existsByIdAndUserId(Long accId, Long userId) {
        return bankAccountUserRepository.existsByIdAndApplicationUser_Id(accId, userId);
    }

    public List<BankAccountUser> findAllByBankAccount_CardNumber(String cardNumber) {
        return bankAccountUserRepository.findAllByBankAccount_CardNumber(cardNumber);
    }

    public BankAccountUser findBankAccountUserById(Long id) {
        return bankAccountUserRepository.findBankAccountUserById(id);
    }

    public PaymentResponse payment(Long accId, Long userId, double amountToPay) {
        if (!existsByIdAndUserId(accId, userId))
            return new PaymentResponse(HttpStatus.EXPECTATION_FAILED, "This account does not belong to this user!");

        BankAccountUser bankAccountUser;
        bankAccountUser = findBankAccountUserById(accId);
        double totalMoney = bankAccountUser.getBankAccount().getBalance();

        if (totalMoney < amountToPay)
            return  new PaymentResponse(HttpStatus.EXPECTATION_FAILED, "Not enough money to pay!");

        totalMoney = totalMoney - amountToPay;
        bankAccountUser.getBankAccount().setBalance(totalMoney);
        save(bankAccountUser);
        return new PaymentResponse(HttpStatus.OK, "Payment successful!");
    }

}
