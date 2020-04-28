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
import java.util.List;
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
                                                    @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult){


        RequestValidator.validateRequest(bindingResult);
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setId(userPrincipal.getId());
        List<Merchant> merchantList=merchantService.find(staticQRRequest.getBusinessName());
        ResponseEntity<MainInfoResponse> responseInfo;
        try {
             responseInfo=restService.getReceiptInfo(staticQRRequest);

        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");

        }
        MainInfoResponse mainInfoResponse=responseInfo.getBody();
        if (mainInfoResponse==null) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
        }
        //dodati provjeru kakav je response main vratio
        if(merchantList.isEmpty()){
            restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Business not registered for this service"), mainInfoResponse.getReceiptId());
            throw new ResourceNotFoundException("Business not registered for this service");
        }
        Merchant merchant=merchantList.get(0);
        Transaction transaction=new Transaction(merchant, applicationUser, mainInfoResponse.getTotalPrice(),
                mainInfoResponse.getService(), mainInfoResponse.getReceiptId(), false);
        Transaction transaction1=transactionService.save(transaction);
        return new TransactionSubmitResponse(transaction1.getId(), mainInfoResponse.getTotalPrice(), mainInfoResponse.getService());
    }

    @PostMapping("/submit/static")
    public PaymentResponse processThePaymentStatic(@Valid @RequestBody PayQRRequest payQRRequest,
                                                   @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult){


        RequestValidator.validateRequest(bindingResult);
        Transaction transaction = transactionService.findByIdAndApplicationUser_Id(payQRRequest.getTransactionId(), userPrincipal.getId());
        if(transaction==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transactionId! Try again");
        }

        if(transaction.getBankAccount()!=null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }
        PaymentResponse paymentResponseReq=processTheRequest(transaction);
        if(paymentResponseReq!=null) return paymentResponseReq;

        TransactionLog transactionLog=new TransactionLog(transaction, false);

        BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(payQRRequest.getBankAccountId());
        if(bankAccountUser==null){
            transactionLogService.save(transactionLog);
            return new PaymentResponse(PaymentStatus.CANCELED, "Nonexistent bank account!");
        }
        transactionLog.setBankAccount(bankAccountUser.getBankAccount());
        PaymentResponse paymentResponse = bankAccountUserService.getPaymentResult(payQRRequest.getBankAccountId(),
                userPrincipal.getId(), transaction.getTotalPrice());

        //Obavjestavamo main samo ako je uplaceno

        if(paymentResponse.getPaymentStatus().equals(PaymentStatus.PAID)) {
            //BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(payQRRequest.getBankAccountId());
            transaction.setBankAccount(bankAccountUser.getBankAccount());
            transaction.setProcessed(true);
            transactionLog.setSuccess(true);
            // TODO: 4/28/2020 vratiti ovo
            /*
            try {
                restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.PAID.toString(),
                        paymentResponse.getMessage()), transaction.getReceiptId());
            } catch (HttpStatusCodeException ex) {
                throw new ResourceNotFoundException("Main server responded with error!");

            }
             */
            transactionService.save(transaction);
        }
        transactionLogService.save(transactionLog);
        return paymentResponse;
    }

    @PostMapping("/submit/dynamic")
    public PaymentResponse processThePaymentDynamic(@Valid @RequestBody DynamicQRRequest dynamicQRRequest,
                                                    @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult){

        RequestValidator.validateRequest(bindingResult);
        List<Merchant> merchantList=merchantService.find(dynamicQRRequest.getBusinessName());
        if(merchantList.isEmpty()){
            restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Business not registered for this service"), dynamicQRRequest.getReceiptId());
            throw new ResourceNotFoundException("Business not registered for this service");
        }

        ApplicationUser applicationUser=new ApplicationUser();
        applicationUser.setId(userPrincipal.getId());
        Merchant merchant=merchantList.get(0);

        Transaction transaction=transactionService.findByReceiptId(dynamicQRRequest.getReceiptId());
        if(transaction==null){
                transaction=new Transaction(merchant, applicationUser, dynamicQRRequest.getTotalPrice(),
                    dynamicQRRequest.getService(), dynamicQRRequest.getReceiptId(), false);
                transaction=transactionService.save(transaction);
        }
        else{
            PaymentResponse paymentResponse=processTheRequest(transaction);
           if(paymentResponse!=null) return paymentResponse;
        }
        TransactionLog transactionLog=new TransactionLog(transaction, false);
        BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(dynamicQRRequest.getBankAccountId());
        if(bankAccountUser==null){
            transactionLogService.save(transactionLog);
            return new PaymentResponse(PaymentStatus.CANCELED, "Nonexistent bank account!");
        }
        transactionLog.setBankAccount(bankAccountUser.getBankAccount());
        PaymentResponse paymentResponse = bankAccountUserService.getPaymentResult(dynamicQRRequest.getBankAccountId(),
                userPrincipal.getId(), dynamicQRRequest.getTotalPrice());

        //Obavjestavamo main samo ako je uspjesno placeno
        if(paymentResponse.getPaymentStatus().equals(PaymentStatus.PAID)){
            //BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(dynamicQRRequest.getBankAccountId());

           // Transaction transaction=new Transaction(merchant, applicationUser, dynamicQRRequest.getTotalPrice(),
                    //dynamicQRRequest.getService(), dynamicQRRequest.getReceiptId(), true);
            transaction.setProcessed(true);
            transaction.setBankAccount(bankAccountUser.getBankAccount());
            // TODO: 4/28/2020 vratiti ovo
            /*
            try {
                restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.PAID.toString(),
                        paymentResponse.getMessage()), dynamicQRRequest.getReceiptId());
            } catch (HttpStatusCodeException ex) {
                throw new ResourceNotFoundException("Main server error!");
            }

             */
            transactionService.save(transaction);
        }
        transactionLogService.save(transactionLog);
        return paymentResponse;
    }

    @PostMapping("/checkbalance")
    public PaymentResponse checkTheBalanceForPayment(@Valid @RequestBody CheckBalanceRequest checkBalanceRequest,
                                            @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult){

        RequestValidator.validateRequest(bindingResult);
        if(checkBalanceRequest.getTransactionId()!=null) {
            Transaction transaction = transactionService.findByIdAndApplicationUser_Id(checkBalanceRequest.getTransactionId(), userPrincipal.getId());
            if (transaction == null) {
                return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transaction id! Try again");
            }
            if (transaction.getProcessed()) {
                return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
            }
            return bankAccountUserService.checkBalanceForPayment(checkBalanceRequest.getBankAccountId(), userPrincipal.getId(),
                    transaction.getTotalPrice());
        }
        else if(checkBalanceRequest.getTotalPrice()!=null){
            return bankAccountUserService.checkBalanceForPayment(checkBalanceRequest.getBankAccountId(), userPrincipal.getId(),
                   checkBalanceRequest.getTotalPrice());
        }
        return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction id or total price" +
                " missing! Please provide one! Try again !");
    }

    //Ipak cemo sa odvojenim rutama
    @PostMapping("/static/cancel")
    public PaymentResponse cancelThePaymentStatic(@Valid @RequestBody NotPayQRRequestStatic notPayQRRequestStatic,
                                                  @CurrentUser UserPrincipal userPrincipal, BindingResult result){

        RequestValidator.validateRequest(result);
        Transaction transaction = transactionService.findByIdAndApplicationUser_Id(notPayQRRequestStatic.getTransactionId(), userPrincipal.getId());
        if(transaction==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transaction id! Try again");
        }
        if(transaction.getProcessed()){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }

        if(processTheRequest(transaction)!=null) return new PaymentResponse(PaymentStatus.CANCELED, "Transaction closed!");

        /*
        try {
            //todo vratiti ovo
            restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Customer decided not to proceed with payment"), transaction.getReceiptId());
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
        }
         */

        //transactionService.delete(transaction.getId());
        return new PaymentResponse(PaymentStatus.CANCELED, "Successfully canceled the payment!");
    }

    @PostMapping("/dynamic/cancel2")
    public PaymentResponse cancelThePaymentDynamic2(@Valid @RequestBody NotPayQRRequestDynamic notPayQRRequestDynamic,
                                                    @CurrentUser UserPrincipal userPrincipal,
                                                    BindingResult bindingResult){
        RequestValidator.validateRequest(bindingResult);
        Transaction transaction=transactionService.findByReceiptId(notPayQRRequestDynamic.getReceiptId());
        List<Merchant> merchantList=merchantService.find(notPayQRRequestDynamic.getBusinessName());
        if(merchantList.isEmpty()){
            restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Business not registered for this service"), notPayQRRequestDynamic.getReceiptId());
            throw new ResourceNotFoundException("Business not registered for this service");
        }

        ApplicationUser applicationUser=new ApplicationUser();
        applicationUser.setId(userPrincipal.getId());
        Merchant merchant=merchantList.get(0);
        if(transaction==null){
            transaction=new Transaction(merchant, applicationUser, notPayQRRequestDynamic.getTotalPrice(),
                    notPayQRRequestDynamic.getService(), notPayQRRequestDynamic.getReceiptId(), false);
            transaction=transactionService.save(transaction);
            transactionLogService.save(new TransactionLog(transaction,false));
        }
        else if(transaction.getProcessed()){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }

        if(processTheRequest(transaction)!=null) return new PaymentResponse(PaymentStatus.CANCELED, "Transaction closed!");

        // TODO: 4/28/2020 vratiti ovo
        /*
        try {
            restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Customer decided not to proceed with payment"), notPayQRRequestDynamic.getReceiptId());
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
        }
         */
        return new PaymentResponse(PaymentStatus.CANCELED, "Successfully canceled the payment!");
    }


    @PostMapping("/dynamic/cancel")
    public PaymentResponse cancelThePaymentDynamic(@Valid @RequestBody NotPayQRRequestDynamic notPayQRRequestDynamic){


        try {
            restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Customer decided not to proceed with payment"), notPayQRRequestDynamic.getReceiptId());
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
        }
        return new PaymentResponse(PaymentStatus.CANCELED, "Successfully canceled the payment!");
    }

    @PostMapping("/proba")
    public PaymentResponse testThePayment(@Valid @RequestBody PayQRRequest payQRRequest,
                                         @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult){

        RequestValidator.validateRequest(bindingResult);

        Transaction transaction = transactionService.findByIdAndApplicationUser_Id(payQRRequest.getTransactionId(), userPrincipal.getId());
        if(transaction==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transaction id! Try again");
        }

        if(transaction.getBankAccount()!=null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }

        PaymentResponse paymentResponse = bankAccountUserService.getPaymentResult(payQRRequest.getBankAccountId(),
                userPrincipal.getId(), transaction.getTotalPrice());

        if(paymentResponse.getPaymentStatus()==(PaymentStatus.PAID)) {
            BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(payQRRequest.getBankAccountId());
            transaction.setBankAccount(bankAccountUser.getBankAccount());
            transaction.setProcessed(true);
            transactionService.save(transaction);
        }
        return paymentResponse;
    }

    @PostMapping("/static/cancel/proba")
    public PaymentResponse cancelThePaymentStaticProba(@Valid @RequestBody NotPayQRRequestStatic notPayQRRequestStatic,
                                                  @CurrentUser UserPrincipal userPrincipal, BindingResult bindingResult){

        RequestValidator.validateRequest(bindingResult);

        Transaction transaction = transactionService.findByIdAndApplicationUser_Id(notPayQRRequestStatic.getTransactionId(), userPrincipal.getId());
        if(transaction==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transaction id! Try again");
        }
        if(transaction.getProcessed()){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }
        transactionService.delete(transaction.getId());
        return new PaymentResponse(PaymentStatus.CANCELED, "Successfully canceled the payment!");
    }

    private PaymentResponse processTheRequest(Transaction transaction){
        Integer attempts=transactionLogService.getNumberOfAttempts(transaction.getId());
        if(attempts>4){
           // updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(), "Transaction closed due to many faulty attempts!"), transaction.getReceiptId());
            return new PaymentResponse(PaymentStatus.PROBLEM, "You cannot longer proceed with payment! You have reached" +
                    " the limit of 5 attempts!");
        }
        Date now=new Date();
        long diffInMillies = Math.abs(now.getTime() - transaction.getCreatedAt().getTime());
        long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if(diff>4){
           // updateTheMainServer(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(), "Transaction expired"), transaction.getReceiptId());
            return new PaymentResponse(PaymentStatus.PROBLEM, "You cannot longer proceed with payment! Transaction closes" +
                    " after 5 minutes!");
        }
        return null;
    }


    private void updateTheMainServer(TransacationSuccessRequest transacationSuccessRequest, String receiptId){
        try {
            restService.updateTransactionStatus(transacationSuccessRequest,receiptId);
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
        }
    }
}
