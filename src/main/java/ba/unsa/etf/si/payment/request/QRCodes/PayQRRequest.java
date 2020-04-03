package ba.unsa.etf.si.payment.request.QRCodes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PayQRRequest {

    //todo
    //Not Blank se koristi za text tj string, pa ne može ovdje
    //Mogu se napraviti custom anotacije
    private Boolean proceed;

    //todo isto
    private Long bankAccountId;

    //@NotBlank
    //todo isto razmatranje
    private Long transactionId;

    public PayQRRequest(@NotBlank Boolean proceed, @NotBlank Long bankAccountId, @NotBlank Long transactionId) {
        this.proceed = proceed;
        this.bankAccountId = bankAccountId;
        this.transactionId = transactionId;
    }

    public Boolean getProceed() {
        return proceed;
    }

    public void setProceed(Boolean proceed) {
        this.proceed = proceed;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}