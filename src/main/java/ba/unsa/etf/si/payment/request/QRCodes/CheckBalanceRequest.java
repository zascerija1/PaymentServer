package ba.unsa.etf.si.payment.request.QRCodes;

import java.io.Serializable;

public class CheckBalanceRequest implements Serializable {
    private Long bankAccountId;
    private Long transactionId;
    private Double totalPrice;

    public CheckBalanceRequest() {
    }

    public CheckBalanceRequest(Long bankAccountId, Long transactionId, Double totalPrice) {
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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
