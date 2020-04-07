package ba.unsa.etf.si.payment.controller;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.MoneyTransfer;
import ba.unsa.etf.si.payment.response.BankAccountDataResponse;
import ba.unsa.etf.si.payment.response.MoneyTransferResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.BankAccountService;
import ba.unsa.etf.si.payment.service.BankAccountUserService;
import ba.unsa.etf.si.payment.service.MoneyTransferService;
import ba.unsa.etf.si.payment.util.MoneyTransferStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/moneyTransfer")
public class MoneyTransferController {
    private final MoneyTransferService moneyTransferService;
    private final BankAccountUserService bankAccountUserService;

    public MoneyTransferController(MoneyTransferService moneyTransferService, BankAccountUserService bankAccountUserService) {
        this.moneyTransferService = moneyTransferService;
        this.bankAccountUserService = bankAccountUserService;
    }

    @GetMapping("/allReceives")
    public MoneyTransferResponse getAllReceives(@CurrentUser UserPrincipal currentUser, BankAccount bankAccount){
        if(!bankAccountUserService.existsByIdAndUserId(bankAccount.getId(),currentUser.getId()))
            return new MoneyTransferResponse(MoneyTransferStatus.CANCELED,"This account does not belong to this user!",null);
        else{
            List<MoneyTransfer> moneyTransfers;
            moneyTransfers = moneyTransferService.findAllReceives(bankAccount);
            return new MoneyTransferResponse(MoneyTransferStatus.OK,"All payments to this account", moneyTransfers);
        }
    }
    @GetMapping("/allSends")
    public MoneyTransferResponse getAllSends(@CurrentUser UserPrincipal currentUser, BankAccount bankAccount){
        if(!bankAccountUserService.existsByIdAndUserId(bankAccount.getId(),currentUser.getId()))
            return new MoneyTransferResponse(MoneyTransferStatus.CANCELED,"This account does not belong to this user!",null);
        else{
            List<MoneyTransfer> moneyTransfers;
            moneyTransfers = moneyTransferService.findAllSends(bankAccount);
            return new MoneyTransferResponse(MoneyTransferStatus.OK,"All payments from this account", moneyTransfers);
        }
    }
}
