package ba.unsa.etf.si.payment.controller;


import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.*;
import ba.unsa.etf.si.payment.request.TransacationSuccessRequest;
import ba.unsa.etf.si.payment.request.qrCodes.*;
import ba.unsa.etf.si.payment.response.MainInfoResponse;
import ba.unsa.etf.si.payment.response.transactionResponse.BankAccountLimitResponse;
import ba.unsa.etf.si.payment.response.transactionResponse.PaymentResponse;
import ba.unsa.etf.si.payment.response.transactionResponse.TransactionSubmitResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.*;
import ba.unsa.etf.si.payment.util.NotificationUtil.MessageConstants;
import ba.unsa.etf.si.payment.util.NotificationUtil.NotificationHandler;
import ba.unsa.etf.si.payment.util.NotificationUtil.NotificationStatus;
import ba.unsa.etf.si.payment.util.NotificationUtil.NotificationType;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import ba.unsa.etf.si.payment.util.RequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    private final NotificationService notificationService;
    private final NotificationHandler notificationHandler;

    public RestTransactionController(RestService restService, MerchantService merchantService, TransactionService transactionService, TransactionLogService transactionLogService, BankAccountService bankAccountService, BankAccountUserService bankAccountUserService, NotificationService notificationService, NotificationHandler notificationHandler) {
        this.restService = restService;
        this.merchantService = merchantService;
        this.transactionService = transactionService;
        this.transactionLogService = transactionLogService;
        this.bankAccountService = bankAccountService;
        this.bankAccountUserService = bankAccountUserService;
        this.notificationService = notificationService;
        this.notificationHandler = notificationHandler;
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
            throw new ResourceNotFoundException("Business not registered for this service");

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
        int attempts = transactionLogService.getNumberOfAttempts(transaction.getId()) + 1;
        if (transaction.getBankAccount() != null || transaction.getPaymentStatus() != PaymentStatus.PENDING || attempts>5)
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");


        PaymentResponse paymentResponseReq = processTheTransactionExpiration(transaction);
        if (paymentResponseReq != null) return paymentResponseReq;
        String message = "";

        TransactionLog transactionLog = new TransactionLog(transaction, PaymentStatus.PROBLEM);

        BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(payQRRequest.getBankAccountId());
        if (bankAccountUser == null) {
            if (attempts > 4) {
                message = MessageConstants.ATTEMPTS;
                updateTransactionStatus(transaction, PaymentStatus.INVALIDATED);
            }
            transactionLogService.save(transactionLog);
            sendTheTransactionNotification(transaction, PaymentStatus.INVALIDATED);
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
            System.out.println("ovdje");
            transaction = updateTransactionStatus(transaction, PaymentStatus.INVALIDATED);
            updateTheMainServer(new TransacationSuccessRequest(paymentResponse.getPaymentStatus().toString(), paymentResponse.getMessage()), transaction.getReceiptId());
            paymentResponse.setMessage(paymentResponse.getMessage() + MessageConstants.ATTEMPTS);

        }
        handleResult(transaction,transactionLog,paymentResponse,bankAccountUser);
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
            throw new ResourceNotFoundException("Business not registered for this service");

        }

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setId(userPrincipal.getId());
        applicationUser.setUsername(userPrincipal.getUsername());
        Integer attempts = 1;
        Transaction transaction = transactionService.findByReceiptId(dynamicQRRequest.getReceiptId());
        if (transaction == null) {
            transaction = new Transaction(merchant, applicationUser, dynamicQRRequest.getTotalPrice(),
                    dynamicQRRequest.getService(), dynamicQRRequest.getReceiptId(), PaymentStatus.PENDING);
            transaction = transactionService.save(transaction);
        } else {
            PaymentResponse paymentResponse = processTheTransactionExpiration(transaction);
            attempts += transactionLogService.getNumberOfAttempts(transaction.getId());
            if (transaction.getBankAccount() != null || transaction.getPaymentStatus() != PaymentStatus.PENDING || attempts>5) {
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
                message = MessageConstants.ATTEMPTS;
                updateTransactionStatus(transaction, PaymentStatus.INVALIDATED);
            }
            sendTheTransactionNotification(transaction, PaymentStatus.INVALIDATED);
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
            transaction = updateTransactionStatus(transaction, PaymentStatus.INVALIDATED);
            updateTheMainServer(new TransacationSuccessRequest(paymentResponse.getPaymentStatus().toString(), paymentResponse.getMessage()), transaction.getReceiptId());
            paymentResponse.setMessage(paymentResponse.getMessage() + MessageConstants.ATTEMPTS);
        }
        handleResult(transaction,transactionLog,paymentResponse,bankAccountUser);
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
            sendTheTransactionNotification(transaction, PaymentStatus.INVALIDATED);
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }

        PaymentResponse paymentResponse = processTheTransactionExpiration(transaction);
        if (paymentResponse != null) return paymentResponse;

        updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(), "Customer decided not to proceed with payment"), transaction.getReceiptId());
        updateTransactionStatus(transaction, PaymentStatus.CANCELED);
        transactionLogService.save(new TransactionLog(transaction, PaymentStatus.CANCELED));
        sendTheTransactionNotification(transaction, PaymentStatus.CANCELED);
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
        applicationUser.setUsername(userPrincipal.getUsername());
        if (transaction == null) {
            transaction = transactionService.save(new Transaction(merchant, applicationUser, notPayQRRequestDynamic.getTotalPrice(),
                    notPayQRRequestDynamic.getService(), notPayQRRequestDynamic.getReceiptId(), PaymentStatus.CANCELED));
        } else if (transaction.getPaymentStatus() != PaymentStatus.PENDING) {
            sendTheTransactionNotification(transaction, PaymentStatus.INVALIDATED);
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }
        if (processTheTransactionExpiration(transaction) != null) {
            sendTheTransactionNotification(transaction, PaymentStatus.INVALIDATED);
            return new PaymentResponse(PaymentStatus.CANCELED, "Transaction closed!");
        }

        updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(), "Customer decided not to proceed with payment"), transaction.getReceiptId());
        updateTransactionStatus(transaction, PaymentStatus.CANCELED);
        transactionLogService.save(new TransactionLog(transaction, PaymentStatus.CANCELED));
        sendTheTransactionNotification(transaction, PaymentStatus.CANCELED);
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

    private Transaction updateTransactionStatus(Transaction transaction, PaymentStatus paymentStatus) {
        transaction.setPaymentStatus(paymentStatus);
        return transactionService.save(transaction);
    }

    private void checkAmountOfTransactionAndSendNotification(Transaction transaction){
        if (transaction.getTotalPrice() > MessageConstants.HUGE_TRANSACTION_LIMIT){
            Notification notification = notificationService
                    .save(new Notification(NotificationStatus.WARNING, NotificationType.TRANSACTION, transaction.getId().toString(),MessageConstants.HUGE_TRANSACTION_MSG + MessageConstants.HUGE_TRANSACTION_LIMIT, transaction.getApplicationUser() ));
            notificationHandler.sendNotification(notification);
        }
    }

    private void sendTheTransactionNotification(Transaction transaction,@NotNull PaymentStatus paymentStatus){
        NotificationStatus notificationStatus = NotificationStatus.ERROR;
        String message = MessageConstants.FAIL_TRANSACTION;
        if(paymentStatus.equals(PaymentStatus.PAID)){
                notificationStatus = NotificationStatus.INFO;
                message = MessageConstants.SUCCESSFULL_TRANSACTION;
        }
        else if(paymentStatus.equals(PaymentStatus.INSUFFICIENT_FUNDS)){
            message = MessageConstants.FAIL_TRANSACTION_FUNDS;
        }
        else if(paymentStatus.equals(PaymentStatus.CANCELED)){
            notificationStatus = NotificationStatus.INFO;
            message = MessageConstants.CANCEL_TRANSACTION;
        }
        Notification notification = notificationService
                .save(new Notification(notificationStatus, NotificationType.TRANSACTION, transaction.getId().toString(),message, transaction.getApplicationUser() ));
        notificationHandler.sendNotification(notification);
    }

    private void checkBalanceAndInform(@NotNull BankAccountUser bankAccountUser){
        if(bankAccountUser.getBankAccount().getBalance() < MessageConstants.WARN_BALANCE){
            Notification notification = notificationService.save(new Notification(NotificationStatus.WARNING,
                    NotificationType.ACCOUNT_BALANCE, bankAccountUser.getId().toString(),
                    MessageConstants.ACCOUNT_BALANCE, bankAccountUser.getApplicationUser()));
            notificationHandler.sendNotification(notification);
        }
        checkMonthlyLimitAndInform(bankAccountUser);
    }

    private void checkMonthlyLimitAndInform (@NotNull BankAccountUser bankAccountUser){
        BankAccountLimitResponse bankAccountLimitResponse = transactionService.checkMonthlyExpenses(bankAccountUser,
                MessageConstants.MONTH_TRANSACTION_LIMIT);
        if (bankAccountLimitResponse.getAboveLimit()){
            Notification notification = notificationService.save(new Notification(NotificationStatus.WARNING,
                    NotificationType.ACCOUNT_BALANCE, bankAccountUser.getId().toString(),
                    MessageConstants.MONTHLY_LIMIT, bankAccountUser.getApplicationUser()));
            notificationHandler.sendNotification(notification);
        }
    }

    private void handleResult(Transaction transaction, TransactionLog transactionLog, PaymentResponse paymentResponse,
                              BankAccountUser bankAccountUser){
        transactionService.save(transaction);
        transactionLog.setPaymentStatus(paymentResponse.getPaymentStatus());
        transactionLogService.save(transactionLog);
        sendTheTransactionNotification(transaction, paymentResponse.getPaymentStatus());
        if(paymentResponse.getPaymentStatus().equals(PaymentStatus.PAID)){
            checkBalanceAndInform(bankAccountUser);
            checkAmountOfTransactionAndSendNotification(transaction);
        }
    }
}
