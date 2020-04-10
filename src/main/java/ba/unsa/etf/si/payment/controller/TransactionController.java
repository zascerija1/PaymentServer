package ba.unsa.etf.si.payment.controller;


import ba.unsa.etf.si.payment.exception.BadRequestException;
import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.request.filters.DateFilterRequest;
import ba.unsa.etf.si.payment.request.filters.PriceFilterRequest;
import ba.unsa.etf.si.payment.response.ApiResponse;
import ba.unsa.etf.si.payment.response.transactionResponse.TransactionDataResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.BankAccountService;
import ba.unsa.etf.si.payment.service.BankAccountUserService;
import ba.unsa.etf.si.payment.service.TransactionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final BankAccountUserService bankAccountUserService;

    public TransactionController(TransactionService transactionService, BankAccountUserService bankAccountUserService, BankAccountService bankAccountService) {
        this.transactionService = transactionService;
        this.bankAccountUserService = bankAccountUserService;
    }

    @GetMapping("/all")
    public List<TransactionDataResponse> getAllTransactions(@CurrentUser UserPrincipal currentUser){
        return transactionService.findAllTransactionsByUserId(currentUser.getId());
    }

    @GetMapping("/recent/{days}")
    public List<TransactionDataResponse> getAllTransactionsBetween(@PathVariable Integer days, @CurrentUser UserPrincipal currentUser){
        if(days <= 0) throw new BadRequestException("Number of days invalid.");
        Date endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DATE, days*(-1));
        Date startDate = cal.getTime();
        return transactionService.findAllTransactionsByUserAndDateBetween(currentUser.getId(), startDate, endDate);
    }

    @PostMapping("/date")
    public List<TransactionDataResponse> getAllTransactionsBetweenDates(@Valid @RequestBody DateFilterRequest dateFilterRequest, @CurrentUser UserPrincipal currentUser){
        //validacija
        if(dateFilterRequest.getStartDate()==null || dateFilterRequest.getEndDate()==null)
            throw new BadRequestException("Request not valid!");
        if(dateFilterRequest.getStartDate().after(dateFilterRequest.getEndDate())){
            throw new BadRequestException("Dates not valid");
        }
        //ima li još nekih validacija provjeriti
        return transactionService.findAllTransactionsByUserAndDateBetween(currentUser.getId(), dateFilterRequest.getStartDate(), dateFilterRequest.getEndDate());
    }

    @PostMapping("/price")
    public List<TransactionDataResponse> getAllTransactionsBetweenPrices(@Valid @RequestBody PriceFilterRequest priceFilterRequest, @CurrentUser UserPrincipal currentUser){
        if(priceFilterRequest.getMinPrice()==null || priceFilterRequest.getMaxPrice()==null)
            throw new BadRequestException("Request not valid");
        if(priceFilterRequest.getMinPrice() > priceFilterRequest.getMinPrice()){
            throw new BadRequestException("Prices not valid");
        }
        return transactionService.findAllTransactionsByUserAndTotalPriceBetween(currentUser.getId(), priceFilterRequest.getMinPrice(), priceFilterRequest.getMaxPrice());
    }

    @GetMapping("/merchant/{merchantName}")
    public List<TransactionDataResponse> getAllTransactionsByMerchantName(@PathVariable String merchantName, @CurrentUser UserPrincipal currentUser){
        return transactionService.findAllTransactionsByUserAndMerchantName(currentUser.getId(), merchantName);
    }

    @GetMapping("/service/{service}")
    public List<TransactionDataResponse> getAllTransactionsByService(@PathVariable String service, @CurrentUser UserPrincipal currentUser){
        return transactionService.findAllTransactionsByUserIdAndService(currentUser.getId(), service);
    }

    @GetMapping("/bankAccount/{bankAccountUserId}")
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
    public ApiResponse deleteTransaction(@PathVariable UUID transactionId){
        transactionService.delete(transactionId);
        return new ApiResponse(true, "Transaction deleted successfully.");
    }
}
