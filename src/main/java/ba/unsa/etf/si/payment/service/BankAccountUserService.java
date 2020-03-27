package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.repository.BankAccountUserRepository;
import ba.unsa.etf.si.payment.response.BankAccountDataResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountUserService {

    private final BankAccountUserRepository bankAccountUserRepository;


    public BankAccountUserService(BankAccountUserRepository bankAccountUserRepository) {
        this.bankAccountUserRepository = bankAccountUserRepository;
    }

    public BankAccountUser save (BankAccountUser bankAccountUser){
       return bankAccountUserRepository.save(bankAccountUser);
    }
    public List<BankAccountDataResponse> findBankAccounts(Long userId){
        return bankAccountUserRepository.findAllByApplicationUser_Id(userId)
                .stream()
                .map(bankAccountUser -> {
                    BankAccount bankAccount=bankAccountUser.getBankAccount();
                    //Very important!!!
                    //we send bankaccountuserid, because we want do delete a row in that table
                    //not a bank account which  actually doesn't belong to our system
                    return new BankAccountDataResponse(bankAccountUser.getId(), bankAccount.getAccountOwner(),
                            bankAccount.getBankName(), bankAccount.getExpiryDate(), bankAccount.getCardNumber());
                })
                .collect(Collectors.toList());
    }

    public void delete (Long id){
        bankAccountUserRepository.deleteById(id);
    }

    public boolean existsByIdAndUserId(Long accId, Long userId){
        return bankAccountUserRepository.existsByIdAndApplicationUser_Id(accId,userId);
    }
}
