package ba.unsa.etf.si.payment.request;

import ba.unsa.etf.si.payment.util.NotificationUtil.MessageConstants;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class BankAccountConfigRequest {

    @NotNull
    @DecimalMin("0.0")
    private Double monthlyLimit;

    @NotNull
    @DecimalMin("0.0")
    private Double balanceLowerLimit;

    @NotNull
    @DecimalMin("0.0")
    private Double transactionAmountLimit;

    public BankAccountConfigRequest() {
    }

    public BankAccountConfigRequest(Double monthlyLimit, Double balanceLowerLimit, Double transactionAmountLimit) {
        this.monthlyLimit = monthlyLimit;
        this.balanceLowerLimit = balanceLowerLimit;
        this.transactionAmountLimit = transactionAmountLimit;
    }

    public Double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(Double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public Double getBalanceLowerLimit() {
        return balanceLowerLimit;
    }

    public void setBalanceLowerLimit(Double balanceLowerLimit) {
        this.balanceLowerLimit = balanceLowerLimit;
    }

    public Double getTransactionAmountLimit() {
        return transactionAmountLimit;
    }

    public void setTransactionAmountLimit(Double transactionAmountLimit) {
        this.transactionAmountLimit = transactionAmountLimit;
    }
}
