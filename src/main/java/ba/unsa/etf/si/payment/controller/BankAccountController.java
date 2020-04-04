package ba.unsa.etf.si.payment.controller;


import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.response.AccountResponse;
import ba.unsa.etf.si.payment.response.BankAccountDataResponse;
import ba.unsa.etf.si.payment.response.DeleteAccountResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.ApplicationUserService;
import ba.unsa.etf.si.payment.service.BankAccountService;
import ba.unsa.etf.si.payment.service.BankAccountUserService;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {
    private final BankAccountUserService bankAccountUserService;
    private final BankAccountService bankAccountService;
    private final ApplicationUserService applicationUserService;


    public BankAccountController(BankAccountUserService bankAccountUserService, BankAccountService bankAccountService, ApplicationUserService applicationUserService) {
        this.bankAccountUserService = bankAccountUserService;
        this.bankAccountService = bankAccountService;
        this.applicationUserService = applicationUserService;
    }

    //All accounts that belong to current user
    @GetMapping("/all")
    public List<BankAccountDataResponse> getBankAccounts(@CurrentUser UserPrincipal currentUser){
        return bankAccountUserService.findBankAccounts(currentUser.getId());
    }

    //Add account
    @PostMapping("/add")
    public AccountResponse addBankAccount(@Valid @RequestBody BankAccount bankAccount, @CurrentUser UserPrincipal currentUser) {
        List<BankAccount> bankAccounts=bankAccountService.find(bankAccount.getCvc(), bankAccount.getCardNumber());
        if(bankAccounts.isEmpty()){
            throw new ResourceNotFoundException("Bank account is not valid!");
        }

        if(!bankAccountUserService.findAllByBankAccount_CardNumber(bankAccounts.get(0).getCardNumber()).isEmpty())
            throw new ResourceNotFoundException("Bank account is in use!");

        //If user doesn't match account owner
        BankAccount acc = bankAccounts.get(0);
        ApplicationUser currUser = applicationUserService.find(currentUser.getId());
        if(!acc.getAccountOwner().equals(currUser.getFirstName()+" "+currUser.getLastName())){
            return new AccountResponse(false, "User and account owner do not match");
        }


        BankAccountUser bankAccountUser=new BankAccountUser();
        ApplicationUser user=new ApplicationUser();
        user.setId(currentUser.getId());
        bankAccountUser.setBankAccount(bankAccounts.get(0));
        bankAccountUser.setApplicationUser(user);
        bankAccountUserService.save(bankAccountUser);
        return new AccountResponse(true, "Succefully added account");
    }
    @DeleteMapping("/delete/{accountId}")
    public DeleteAccountResponse deleteBankAccounts(@PathVariable Long accountId,
                                   @CurrentUser UserPrincipal currentUser){

        if(!bankAccountUserService.existsByIdAndUserId(accountId,currentUser.getId())){
            return new DeleteAccountResponse(false, "Account does not exist!");
        }
        bankAccountUserService.delete(accountId);
        return new DeleteAccountResponse(true, "Successful deletion!");
    }


}
