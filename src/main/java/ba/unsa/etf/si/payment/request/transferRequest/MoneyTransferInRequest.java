package ba.unsa.etf.si.payment.request.transferRequest;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MoneyTransferInRequest {
    @NotNull
    @Min(0)
    private Long sourceBankAccount;

    @NotNull
    @Min(0)
    private Long destinationBankAccount;

    @NotNull
    @DecimalMin("0.0")
    private Double amount;

    public MoneyTransferInRequest() {
    }

    public MoneyTransferInRequest(Long sourceBankAccount, Long destinationBankAccount, Double amount) {
        this.sourceBankAccount = sourceBankAccount;
        this.destinationBankAccount = destinationBankAccount;
        this.amount = amount;
    }

    public Long getSourceBankAccount() {
        return sourceBankAccount;
    }

    public void setSourceBankAccount(Long sourceBankAccount) {
        this.sourceBankAccount = sourceBankAccount;
    }

    public Long getDestinationBankAccount() {
        return destinationBankAccount;
    }

    public void setDestinationBankAccount(Long destinationBankAccount) {
        this.destinationBankAccount = destinationBankAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
