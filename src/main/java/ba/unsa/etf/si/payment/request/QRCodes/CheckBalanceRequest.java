package ba.unsa.etf.si.payment.request.QRCodes;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

public class CheckBalanceRequest implements Serializable {

    @NotNull
    @Min(0)
    private Long bankAccountId;

    @NotNull
    private UUID transactionId;

    @NotNull
    @DecimalMin("0.0")
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
