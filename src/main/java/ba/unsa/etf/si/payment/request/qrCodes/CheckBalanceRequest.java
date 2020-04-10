package ba.unsa.etf.si.payment.request.qrCodes;

import java.io.Serializable;
import java.util.UUID;

public class CheckBalanceRequest implements Serializable {
    private Long bankAccountId;
    private UUID transactionId;
    private Double totalPrice;

    public CheckBalanceRequest() {
    }

    public CheckBalanceRequest(Long bankAccountId, UUID transactionId, Double totalPrice) {
        this.bankAccountId = bankAccountId;
        this.transactionId = transactionId;
        this.totalPrice = totalPrice;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
