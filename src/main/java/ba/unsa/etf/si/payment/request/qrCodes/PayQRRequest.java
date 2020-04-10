package ba.unsa.etf.si.payment.request.qrCodes;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class PayQRRequest {


    @NotNull
    @Min(0)
    private Long bankAccountId;

    @NotNull
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