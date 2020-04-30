package ba.unsa.etf.si.payment.controller;


import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.*;
import ba.unsa.etf.si.payment.request.qrCodes.*;
import ba.unsa.etf.si.payment.request.TransacationSuccessRequest;
import ba.unsa.etf.si.payment.response.*;
import ba.unsa.etf.si.payment.response.transactionResponse.PaymentResponse;
import ba.unsa.etf.si.payment.response.transactionResponse.TransactionSubmitResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.*;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import ba.unsa.etf.si.payment.util.RequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/payments")
public class RestTransactionController {

    private final RestService restService;
    private final MerchantService merchantService;
    private final TransactionService transactionService;
    private final TransactionLogService transactionLogService;
    private final BankAccountService bankAccountService;
    private final BankAccountUserService bankAccountUserService;
    private final String attemptsMessage = " You have reached the limit od 5 attempts!";

    public RestTransactionController(RestService restService, MerchantService merchantService, TransactionService transactionService, TransactionLogService transactionLogService, BankAccountService bankAccountService, BankAccountUserService bankAccountUserService) {
        this.restService = restService;
        this.merchantService = merchantService;
        this.transactionService = transactionService;
        this.transactionLogService = transactionLogService;
        this.bankAccountService = bankAccountService;
        this.bankAccountUserService = bankAccountUserService;
    }


    @PostMapping("/receipt/info")
    public TransactionSubmitResponse getPaymentInfo(@Valid @RequestBody StaticQRRequest staticQRRequest,
                                                    @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult) {


        RequestValidator.validateRequest(bindingResult);
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setId(userPrincipal.getId());
        Merchant merchant = merchantService.find(staticQRRequest.getBusinessName());
        ResponseEntity<MainInfoResponse> responseInfo;
        try {
            responseInfo = restService.getReceiptInfo(staticQRRequest);

        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");

        }
        MainInfoResponse mainInfoResponse = responseInfo.getBody();
        if (mainInfoResponse == null) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
        }
        //dodati provjeru kakav je response main vratio
        if (merchant == null) {
            updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Business not registered for this service"), mainInfoResponse.getReceiptId());
        }
        Transaction transaction = new Transaction(merchant, applicationUser, mainInfoResponse.getTotalPrice(),
                mainInfoResponse.getService(), mainInfoResponse.getReceiptId(), PaymentStatus.PENDING);
        Transaction transaction1 = transactionService.save(transaction);
        return new TransactionSubmitResponse(transaction1.getId(), mainInfoResponse.getTotalPrice(), mainInfoResponse.getService());
    }

    @PostMapping("/submit/static")
    public PaymentResponse processThePaymentStatic(@Valid @RequestBody PayQRRequest payQRRequest,
                                                   @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult) {


        RequestValidator.validateRequest(bindingResult);
        Transaction transaction = transactionService.findByIdAndApplicationUser_Id(payQRRequest.getTransactionId(), userPrincipal.getId());
        if (transaction == null)
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transactionId! Try again");

        if (transaction.getBankAccount() != null || transaction.getPaymentStatus() != PaymentStatus.PENDING)
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");


        PaymentResponse paymentResponseReq = processTheTransactionExpiration(transaction);
        if (paymentResponseReq != null) return paymentResponseReq;
        int attempts = transactionLogService.getNumberOfAttempts(transaction.getId()) + 1;
        String message = "";

        TransactionLog transactionLog = new TransactionLog(transaction, PaymentStatus.PROBLEM);

        BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(payQRRequest.getBankAccountId());
        if (bankAccountUser == null) {
            if (attempts > 4) {
                message = attemptsMessage;
                updateTransactionStatus(transaction, PaymentStatus.INVALIDATED);
            }
            transactionLogService.save(transactionLog);
            return new PaymentResponse(PaymentStatus.CANCELED, "Nonexistent bank account!" + message);
        }
        transactionLog.setBankAccount(bankAccountUser.getBankAccount());
        PaymentResponse paymentResponse = bankAccountUserService.getPaymentResult(payQRRequest.getBankAccountId(),
                userPrincipal.getId(), transaction.getTotalPrice());

        if (paymentResponse.getPaymentStatus().equals(PaymentStatus.PAID)) {
            transaction.setBankAccount(bankAccountUser.getBankAccount());
            transaction.setPaymentStatus(PaymentStatus.PAID);
            Merchant merchant = transaction.getMerchant();
            merchant.getBankAccount().putIntoAccount(transaction.getTotalPrice());
            bankAccountService.save(merchant.getBankAccount());
            updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.PAID.toString(), paymentResponse.getMessage()), transaction.getReceiptId());
        } else if (attempts > 4) {
            transaction.setPaymentStatus(PaymentStatus.INVALIDATED);
            updateTheMainServer(new TransacationSuccessRequest(paymentResponse.getPaymentStatus().toString(), paymentResponse.getMessage()), transaction.getReceiptId());
            paymentResponse.setMessage(paymentResponse.getMessage() + attemptsMessage);

        }
        transactionService.save(transaction);
        transactionLog.setPaymentStatus(paymentResponse.getPaymentStatus());
        transactionLogService.save(transactionLog);
        return paymentResponse;
    }

    @PostMapping("/submit/dynamic")
    public PaymentResponse processThePaymentDynamic(@Valid @RequestBody DynamicQRRequest dynamicQRRequest,
                                                    @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult) {

        RequestValidator.validateRequest(bindingResult);
        Merchant merchant = merchantService.find(dynamicQRRequest.getBusinessName());
        if (merchant == null) {
            updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Business not registered for this service"), dynamicQRRequest.getReceiptId());
        }

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setId(userPrincipal.getId());
        Integer attempts = 1;
        Transaction transaction = transactionService.findByReceiptId(dynamicQRRequest.getReceiptId());
        if (transaction == null) {
            transaction = new Transaction(merchant, applicationUser, dynamicQRRequest.getTotalPrice(),
                    dynamicQRRequest.getService(), dynamicQRRequest.getReceiptId(), PaymentStatus.PENDING);
            transaction = transactionService.save(transaction);
        } else {
            PaymentResponse paymentResponse = processTheTransactionExpiration(transaction);
            attempts += transactionLogService.getNumberOfAttempts(transaction.getId());
            if (transaction.getBankAccount() != null || transaction.getPaymentStatus() != PaymentStatus.PENDING) {
                return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
            }
            if (paymentResponse != null) return paymentResponse;
        }
        String message = "";
        TransactionLog transactionLog = new TransactionLog(transaction, PaymentStatus.PROBLEM);
        BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(dynamicQRRequest.getBankAccountId());
        if (bankAccountUser == null) {
            transactionLogService.save(transactionLog);
            if (attempts > 4) {
                message = attemptsMessage;
                updateTransactionStatus(transaction, PaymentStatus.INVALIDATED);
            }
            return new PaymentResponse(PaymentStatus.CANCELED, "Nonexistent bank account!" + message);
        }
        transactionLog.setBankAccount(bankAccountUser.getBankAccount());
        PaymentResponse paymentResponse = bankAccountUserService.getPaymentResult(dynamicQRRequest.getBankAccountId(),
                userPrincipal.getId(), dynamicQRRequest.getTotalPrice());

        if (paymentResponse.getPaymentStatus().equals(PaymentStatus.PAID)) {
            transaction.setPaymentStatus(PaymentStatus.PAID);
            transaction.setBankAccount(bankAccountUser.getBankAccount());
            merchant.getBankAccount().putIntoAccount(transaction.getTotalPrice());
            bankAccountService.save(merchant.getBankAccount());
            updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.PAID.toString(), paymentResponse.getMessage()), transaction.getReceiptId());
        } else if (attempts > 4) {
            transaction.setPaymentStatus(PaymentStatus.INVALIDATED);
            updateTheMainServer(new TransacationSuccessRequest(paymentResponse.getPaymentStatus().toString(), paymentResponse.getMessage()), transaction.getReceiptId());
            paymentResponse.setMessage(paymentResponse.getMessage() + attemptsMessage);

        }
        transactionService.save(transaction);
        transactionLog.setPaymentStatus(paymentResponse.getPaymentStatus());
        transactionLogService.save(transactionLog);
        return paymentResponse;
    }

    @PostMapping("/checkbalance")
    public PaymentResponse checkTheBalanceForPayment(@Valid @RequestBody CheckBalanceRequest checkBalanceRequest,
                                                     @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult) {

        RequestValidator.validateRequest(bindingResult);
        if (checkBalanceRequest.getTransactionId() != null) {
            Transaction transaction = transactionService.findByIdAndApplicationUser_Id(checkBalanceRequest.getTransactionId(), userPrincipal.getId());
            if (transaction == null) {
                return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transaction id! Try again");
            }

            if (transaction.getPaymentStatus() != PaymentStatus.PENDING) {
                return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
            }
            return bankAccountUserService.checkBalanceForPayment(checkBalanceRequest.getBankAccountId(), userPrincipal.getId(),
                    transaction.getTotalPrice());
        } else if (checkBalanceRequest.getTotalPrice() != null) {
            return bankAccountUserService.checkBalanceForPayment(checkBalanceRequest.getBankAccountId(), userPrincipal.getId(),
                    checkBalanceRequest.getTotalPrice());
        }
        return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction id or total price" +
                " missing! Please provide one! Try again !");
    }

    //Ipak cemo sa odvojenim rutama
    @PostMapping("/static/cancel")
    public PaymentResponse cancelThePaymentStatic(@Valid @RequestBody NotPayQRRequestStatic notPayQRRequestStatic,
                                                  @CurrentUser UserPrincipal userPrincipal, BindingResult result) {

        RequestValidator.validateRequest(result);
        Transaction transaction = transactionService.findByIdAndApplicationUser_Id(notPayQRRequestStatic.getTransactionId(), userPrincipal.getId());
        if (transaction == null) {
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transaction id! Try again");
        }
        if (transaction.getPaymentStatus() != PaymentStatus.PENDING) {
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }

        PaymentResponse paymentResponse = processTheTransactionExpiration(transaction);
        if (paymentResponse != null) return paymentResponse;

        updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(), "Customer decided not to proceed with payment"), transaction.getReceiptId());
        updateTransactionStatus(transaction, PaymentStatus.CANCELED);
        transactionLogService.save(new TransactionLog(transaction, PaymentStatus.CANCELED));
        return new PaymentResponse(PaymentStatus.CANCELED, "Successfully canceled the payment!");
    }

    @PostMapping("/dynamic/cancel")
    public PaymentResponse cancelThePaymentDynamic(@Valid @RequestBody NotPayQRRequestDynamic notPayQRRequestDynamic,
                                                   @CurrentUser UserPrincipal userPrincipal,
                                                   BindingResult bindingResult) {
        RequestValidator.validateRequest(bindingResult);
        Transaction transaction = transactionService.findByReceiptId(notPayQRRequestDynamic.getReceiptId());
        Merchant merchant = merchantService.find(notPayQRRequestDynamic.getBusinessName());
        if (merchant == null) {
            updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Business not registered for this service"), notPayQRRequestDynamic.getReceiptId());
        }

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setId(userPrincipal.getId());
        if (transaction == null) {
            transaction = new Transaction(merchant, applicationUser, notPayQRRequestDynamic.getTotalPrice(),
                    notPayQRRequestDynamic.getService(), notPayQRRequestDynamic.getReceiptId(), PaymentStatus.CANCELED);
            transaction = transactionService.save(transaction);

        } else if (transaction.getPaymentStatus() != PaymentStatus.PENDING) {
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }
        if (processTheTransactionExpiration(transaction) != null)
            return new PaymentResponse(PaymentStatus.CANCELED, "Transaction closed!");

        updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(), "Customer decided not to proceed with payment"), transaction.getReceiptId());
        updateTransactionStatus(transaction, PaymentStatus.CANCELED);
        transactionLogService.save(new TransactionLog(transaction, PaymentStatus.CANCELED));
        return new PaymentResponse(PaymentStatus.CANCELED, "Successfully canceled the payment!");
    }

    private PaymentResponse processTheTransactionExpiration(Transaction transaction) {

        Date now = new Date();
        long diffInMillies = Math.abs(now.getTime() - transaction.getCreatedAt().getTime());
        long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (diff > 4) {
            updateTransactionStatus(transaction, PaymentStatus.INVALIDATED);
            updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(), "Transaction expired"), transaction.getReceiptId());
            return new PaymentResponse(PaymentStatus.PROBLEM, "You cannot longer access this transaction! Transaction closes" +
                    " after 5 minutes!");
        }
        return null;
    }

    private void updateTheMainServer(TransacationSuccessRequest transacationSuccessRequest, String receiptId) {
        try {
            restService.updateTransactionStatus(transacationSuccessRequest, receiptId);
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
        }
    }

    private void updateTransactionStatus(Transaction transaction, PaymentStatus paymentStatus) {
        transaction.setPaymentStatus(paymentStatus);
        transactionService.save(transaction);
    }
}
