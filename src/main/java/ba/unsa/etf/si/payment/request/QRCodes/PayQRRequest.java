package ba.unsa.etf.si.payment.request.QRCodes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PayQRRequest {

    //todo
    //Not Blank se koristi za text tj string, pa ne može ovdje
    //Mogu se napraviti custom anotacije
    //todo isto
    private Long bankAccountId;

    //@NotBlank
    //todo isto razmatranje
    private Long transactionId;

    public PayQRRequest(Long bankAccountId,Long transactionId) {
        this.bankAccountId = bankAccountId;
        this.transactionId = transactionId;
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