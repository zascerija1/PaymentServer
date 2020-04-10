package ba.unsa.etf.si.payment.request.qrCodes;

import java.util.UUID;

public class PayQRRequest {

    //todo
    //Not Blank se koristi za text tj string, pa ne može ovdje
    //Mogu se napraviti custom anotacije
    //todo isto
    private Long bankAccountId;

    //@NotBlank
    //todo isto razmatranje
    private UUID transactionId;

    public PayQRRequest(Long bankAccountId,UUID transactionId) {
        this.bankAccountId = bankAccountId;
        this.transactionId = transactionId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }


    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}