package ba.unsa.etf.si.payment.controller;


import ba.unsa.etf.si.payment.exception.BadRequestException;
import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.response.TransactionDataResponse;
import ba.unsa.etf.si.payment.response.DeleteTransactionResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.BankAccountService;
import ba.unsa.etf.si.payment.service.BankAccountUserService;
import ba.unsa.etf.si.payment.service.TransactionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final BankAccountUserService bankAccountUserService;
    private final BankAccountService bankAccountService;

    public TransactionController(TransactionService transactionService, BankAccountUserService bankAccountUserService, BankAccountService bankAccountService) {
        this.transactionService = transactionService;
        this.bankAccountUserService = bankAccountUserService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/all")
    public List<TransactionDataResponse> getAllTransactions(@CurrentUser UserPrincipal currentUser){
        return transactionService.findAllTransactionsByUserId(currentUser.getId());
    }

    @GetMapping("/recent/{days}")
    public List<TransactionDataResponse> getAllTransactionsBetween(@PathVariable Integer days){
        if(days <= 0) throw new BadRequestException("Number of days invalid.");
        Date endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DATE, days*(-1));
        Date startDate = cal.getTime();
        return transactionService.findAllTransactionsBetween(startDate, endDate);
    }

    @GetMapping("/merchant/{merchantName}")
    public List<TransactionDataResponse> getAllTransactionsByMerchantName(@PathVariable String merchantName, @CurrentUser UserPrincipal currentUser){
        return transactionService.findAllTransactionsByUserAndMerchantName(currentUser.getId(), merchantName);
    }

    @GetMapping("/service/{service}")
    public List<TransactionDataResponse> getAllTransactionsByService(@PathVariable String service, @CurrentUser UserPrincipal currentUser){
        return transactionService.findAllTransactionsByUserIdAndService(currentUser.getId(), service);
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


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{transactionId}")
    public DeleteTransactionResponse deleteTransaction(@PathVariable UUID transactionId){
        transactionService.delete(transactionId);
        return new DeleteTransactionResponse(true, "Transaction deleted successfully.");
    }
}
