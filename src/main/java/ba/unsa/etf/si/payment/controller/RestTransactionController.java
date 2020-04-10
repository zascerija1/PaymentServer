package ba.unsa.etf.si.payment.controller;


import ba.unsa.etf.si.payment.exception.BadRequestException;
import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.model.Merchant;
import ba.unsa.etf.si.payment.model.Transaction;
import ba.unsa.etf.si.payment.request.qrCodes.*;
import ba.unsa.etf.si.payment.request.TransacationSuccessRequest;
import ba.unsa.etf.si.payment.response.*;
import ba.unsa.etf.si.payment.response.transactionResponse.PaymentResponse;
import ba.unsa.etf.si.payment.response.transactionResponse.TransactionSubmitResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.BankAccountUserService;
import ba.unsa.etf.si.payment.service.MerchantService;
import ba.unsa.etf.si.payment.service.RestService;
import ba.unsa.etf.si.payment.service.TransactionService;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class RestTransactionController {

    private final RestService restService;
    private final MerchantService merchantService;
    private final TransactionService transactionService;
    private final BankAccountUserService bankAccountUserService;

    public RestTransactionController(RestService restService, MerchantService merchantService, TransactionService transactionService, BankAccountUserService bankAccountUserService) {
        this.restService = restService;
        this.merchantService = merchantService;
        this.transactionService = transactionService;
        this.bankAccountUserService = bankAccountUserService;
    }


    @PostMapping("/receipt/info")
    //todo ovo je proba
    public TransactionSubmitResponse getPaymentInfo(@Valid @RequestBody StaticQRRequest staticQRRequest,
                                                    @CurrentUser UserPrincipal userPrincipal){

        //todo dodai neku validator metodu neke mozda staticke klase da validira staticQRrequest jer se ne moza na long
        //stavljati notBlank
        //postoji varijanta da se naprave custom anotacije

        if(staticQRRequest==null || staticQRRequest.getCashRegisterId()==null || staticQRRequest.getOfficeId()==null){
            throw new BadRequestException("Bad request!");
        }

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
                                                   @CurrentUser UserPrincipal userPrincipal){


        if(payQRRequest==null || payQRRequest.getTransactionId()==null || payQRRequest.getBankAccountId()==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Parameters" +
                    " missing! Try again");
        }

        Transaction transaction = transactionService.findByIdAndApplicationUser_Id(payQRRequest.getTransactionId(), userPrincipal.getId());
        if(transaction==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transactionId! Try again");
        }

        if(transaction.getBankAccount()!=null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }

        //todo provjeriti ovo!!!!!!!!!!!!!!!!

       PaymentResponse paymentResponse = bankAccountUserService.getPaymentResult(payQRRequest.getBankAccountId(),
                userPrincipal.getId(), transaction.getTotalPrice());

        //Obavjestavamo main samo ako je uplaceno

        if(paymentResponse.getPaymentStatus().equals(PaymentStatus.PAID)) {
            BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(payQRRequest.getBankAccountId());
            transaction.setBankAccount(bankAccountUser.getBankAccount());
            transaction.setProcessed(true);
            try {
                restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.PAID.toString(),
                        paymentResponse.getMessage()), transaction.getReceiptId());
            } catch (HttpStatusCodeException ex) {
                throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");

            }

            transactionService.save(transaction);
        }
        return paymentResponse;
    }

    @PostMapping("/submit/dynamic")
    public PaymentResponse processThePaymentDynamic(@Valid @RequestBody DynamicQRRequest dynamicQRRequest,
                                                    @CurrentUser UserPrincipal userPrincipal){


        if(dynamicQRRequest==null || dynamicQRRequest.getBankAccountId()==null
                || dynamicQRRequest.getTotalPrice()==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Parameters" +
                    " missing! Try again");
        }
        List<Merchant> merchantList=merchantService.find(dynamicQRRequest.getBusinessName());
        if(merchantList.isEmpty()){
            restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Business not registered for this service"), dynamicQRRequest.getReceiptId());
            throw new ResourceNotFoundException("Business not registered for this service");
        }

        PaymentResponse paymentResponse = bankAccountUserService.getPaymentResult(dynamicQRRequest.getBankAccountId(),
                userPrincipal.getId(), dynamicQRRequest.getTotalPrice());

        //Obavjestavamo main samo ako je uspjesno placeno

        if(paymentResponse.getPaymentStatus().equals(PaymentStatus.PAID)){
            BankAccountUser bankAccountUser = bankAccountUserService.findBankAccountUserById(dynamicQRRequest.getBankAccountId());
            ApplicationUser applicationUser=new ApplicationUser();
            applicationUser.setId(userPrincipal.getId());
            Merchant merchant=merchantList.get(0);
            Transaction transaction=new Transaction(merchant, applicationUser, dynamicQRRequest.getTotalPrice(),
                    dynamicQRRequest.getService(), dynamicQRRequest.getReceiptId(), true);
            transaction.setBankAccount(bankAccountUser.getBankAccount());
            try {
                restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.PAID.toString(),
                        paymentResponse.getMessage()), dynamicQRRequest.getReceiptId());
            } catch (HttpStatusCodeException ex) {
                throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
            }

            transactionService.save(transaction);
        }
        return paymentResponse;
    }


    //Uklonila valid jer ne treba proceed parametar
    @PostMapping("/checkbalance")
    public PaymentResponse checkTheBalanceForPayment(@RequestBody CheckBalanceRequest checkBalanceRequest,
                                            @CurrentUser UserPrincipal userPrincipal){
        if(checkBalanceRequest==null ||  checkBalanceRequest.getBankAccountId()==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Bank account" +
                    " missing! Try again");
        }
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
    public PaymentResponse cancelThePaymentStatic(@RequestBody NotPayQRRequestStatic notPayQRRequestStatic,
                                             @CurrentUser UserPrincipal userPrincipal){
        if(notPayQRRequestStatic ==null || notPayQRRequestStatic.getTransactionId()==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction id missing! Try again");
        }
        Transaction transaction = transactionService.findByIdAndApplicationUser_Id(notPayQRRequestStatic.getTransactionId(), userPrincipal.getId());
        if(transaction==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Wrong transaction id! Try again");
        }
        if(transaction.getProcessed()){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction already processed!");
        }

        try {
            restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Customer decided not to proceed with payment"), transaction.getReceiptId());
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
        }

        transactionService.delete(transaction.getId());
        return new PaymentResponse(PaymentStatus.CANCELED, "Successfully canceled the payment!");
    }

    @PostMapping("/dynamic/cancel")
    public PaymentResponse cancelThePaymentDynamic(@Valid @RequestBody NotPayQRRequestDynamic notPayQRRequestDynamic,
                                                   @CurrentUser UserPrincipal userPrincipal){

        try {
            restService.updateTransactionStatus(new TransacationSuccessRequest(PaymentStatus.CANCELED.toString(),
                    "Customer decided not to proceed with payment"), notPayQRRequestDynamic.getReceiptId());
        } catch (HttpStatusCodeException ex) {
            throw new ResourceNotFoundException("Receipt data could not be loaded from main server!");
        }
        return new PaymentResponse(PaymentStatus.CANCELED, "Successfully canceled the payment!");
    }

    //todo Proba
    //Ovdje mozete probati proces placanja za staticki kod
    //S tim da u bazi mora biti neka transakcija prije toga
    //Bez ubacenonog bank accounta
    //I processed false jer kao nije obrađena
    @PostMapping("/proba")
    public PaymentResponse testThePayment(@Valid @RequestBody PayQRRequest payQRRequest,
                                         @CurrentUser UserPrincipal userPrincipal){

        if(payQRRequest==null || payQRRequest.getTransactionId()==null || payQRRequest.getBankAccountId()==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction or bank account id" +
                    " missing! Try again");
        }

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
    public PaymentResponse cancelThePaymentStaticProba(@RequestBody NotPayQRRequestStatic notPayQRRequestStatic,
                                                  @CurrentUser UserPrincipal userPrincipal){
        if(notPayQRRequestStatic ==null || notPayQRRequestStatic.getTransactionId()==null){
            return new PaymentResponse(PaymentStatus.PROBLEM, "Problem occured! Transaction id missing! Try again");
        }
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
}
