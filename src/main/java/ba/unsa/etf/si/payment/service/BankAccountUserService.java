package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.repository.BankAccountUserRepository;
import ba.unsa.etf.si.payment.request.BankAccountConfigRequest;
import ba.unsa.etf.si.payment.response.ApiResponse;
import ba.unsa.etf.si.payment.response.BankAccountDataResponse;
import ba.unsa.etf.si.payment.response.transactionResponse.PaymentResponse;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountUserService {

    private final BankAccountUserRepository bankAccountUserRepository;
    private final TransactionService transactionService;


    public BankAccountUserService(BankAccountUserRepository bankAccountUserRepository, TransactionService transactionService) {
        this.bankAccountUserRepository = bankAccountUserRepository;
        this.transactionService = transactionService;
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
                            bankAccount.getBank().getBankName(), bankAccount.getExpiryDate(), bankAccount.getCardNumber(),
                            bankAccountUser.getMonthlyLimit(), bankAccountUser.getBalanceLowerLimit(),
                            bankAccountUser.getTransactionAmountLimit());
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

    public BankAccountUser findBankAccountUserByIdAndApplicationUserId(Long bankAccountUserId, Long applicationUserId) {
        return bankAccountUserRepository.findByIdAndApplicationUser_Id(bankAccountUserId,applicationUserId);
    }

    public PaymentResponse getPaymentResult(Long accId, Long userId, double amountToPay) {
        if (!existsByIdAndUserId(accId, userId))
            return new PaymentResponse(PaymentStatus.CANCELED, "This account does not belong to this user!");

        BankAccountUser bankAccountUser;
        bankAccountUser = findBankAccountUserById(accId);
        double totalMoney = bankAccountUser.getBankAccount().getBalance();

        if (totalMoney < amountToPay)
            return new PaymentResponse(PaymentStatus.INSUFFICIENT_FUNDS, "Not enough money to pay!");

        totalMoney = totalMoney - amountToPay;
        bankAccountUser.getBankAccount().setBalance(totalMoney);
        save(bankAccountUser);
        return new PaymentResponse(PaymentStatus.PAID, "Payment successful!");
    }

    public PaymentResponse checkBalanceForPayment(Long accId, Long userId, double amountToPay) {
        if (!existsByIdAndUserId(accId, userId))
            return new PaymentResponse(PaymentStatus.CANCELED, "This account does not belong to this user!");

        BankAccountUser bankAccountUser=findBankAccountUserById(accId);
        double totalMoney = bankAccountUser.getBankAccount().getBalance();

        if (totalMoney < amountToPay)
            return new PaymentResponse(PaymentStatus.INSUFFICIENT_FUNDS, "Not enough money to pay!");

        return new PaymentResponse(PaymentStatus.SUFFICIENT_FUNDS, "You have enough funds to pay this receipt!");
    }

    public ApiResponse updateAccountConfig(Long bankAccountUserId, Long userId, BankAccountConfigRequest bankAccountConfigRequest){
        BankAccountUser bankAccountUser = findBankAccountUserByIdAndApplicationUserId(bankAccountUserId, userId);
        if (bankAccountUser == null)
            return new ApiResponse(false, "Account does not belong to user!");
        bankAccountUser.setConfig(bankAccountConfigRequest);
        save(bankAccountUser);
        return new ApiResponse(true, "You have successfully updated bank account details!");
    }

}
