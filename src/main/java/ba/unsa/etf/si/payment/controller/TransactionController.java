package ba.unsa.etf.si.payment.controller;


import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.response.TransactionDataResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.BankAccountUserService;
import ba.unsa.etf.si.payment.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final BankAccountUserService bankAccountUserService;

    public TransactionController(TransactionService transactionService, BankAccountUserService bankAccountUserService) {
        this.transactionService = transactionService;
        this.bankAccountUserService = bankAccountUserService;
    }

    @GetMapping("/all")
    public List<TransactionDataResponse> getAllTransactions(@CurrentUser UserPrincipal currentUser){
        return transactionService.findAllTransactionsByUserId(currentUser.getId());
    }

    @GetMapping("/{bankAccountUserId}")
    public List<TransactionDataResponse> getAllTransactionsByBankAccount(@PathVariable Long bankAccountUserId,
                                                                         @CurrentUser UserPrincipal currentUser){
        BankAccountUser bankAccountUser=bankAccountUserService.
                findBankAccountUserByIdAndApplicationUserId(bankAccountUserId,currentUser.getId());
        if (bankAccountUser==null){
            throw new ResourceNotFoundException("Bank Account does not belong to current user!");
        }
        //todo razmisliti trebaju li jos neke provjere i to
        return transactionService.findAllTransactionsByBankAccount(bankAccountUser.getBankAccount().getId());
    }


}
