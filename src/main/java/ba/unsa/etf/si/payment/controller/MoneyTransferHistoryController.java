package ba.unsa.etf.si.payment.controller;

import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.response.Transfer.MoneyTransferResponse;
import ba.unsa.etf.si.payment.response.Transfer.TransferResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.BankAccountUserService;
import ba.unsa.etf.si.payment.service.MoneyTransferService;
import ba.unsa.etf.si.payment.util.MoneyTransferStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/moneyTransfer")
public class MoneyTransferHistoryController {
    private final MoneyTransferService moneyTransferService;
    private final BankAccountUserService bankAccountUserService;

    public MoneyTransferHistoryController(MoneyTransferService moneyTransferService, BankAccountUserService bankAccountUserService) {
        this.moneyTransferService = moneyTransferService;
        this.bankAccountUserService = bankAccountUserService;
    }

    @GetMapping("/allReceives/{bankAccountUserId}")
    public MoneyTransferResponse getAllReceives(@PathVariable Long bankAccountUserId, @CurrentUser UserPrincipal currentUser){
        if(!bankAccountUserService.existsByIdAndUserId(bankAccountUserId,currentUser.getId()))
            return new MoneyTransferResponse(MoneyTransferStatus.CANCELED,"This account does not belong to this user!",null);
        else{
            List<TransferResponse> moneyTransfers;
            BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(bankAccountUserId);
            moneyTransfers = moneyTransferService.findAllReceives(bankAccountUser.getBankAccount());
            return new MoneyTransferResponse(MoneyTransferStatus.OK,"All payments to this account", moneyTransfers);
        }
    }
    @GetMapping("/allSends/{bankAccountUserId}")
    public MoneyTransferResponse getAllSends(@PathVariable Long bankAccountUserId, @CurrentUser UserPrincipal currentUser){
        if(!bankAccountUserService.existsByIdAndUserId(bankAccountUserId,currentUser.getId()))
            return new MoneyTransferResponse(MoneyTransferStatus.CANCELED,"This account does not belong to this user!",null);
        else{
            List<TransferResponse> moneyTransfers;
            BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(bankAccountUserId);
            moneyTransfers = moneyTransferService.findAllSends(bankAccountUser.getBankAccount());
            return new MoneyTransferResponse(MoneyTransferStatus.OK,"All payments from this account", moneyTransfers);
        }
    }
}
