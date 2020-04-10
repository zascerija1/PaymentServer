package ba.unsa.etf.si.payment.request.transferRequest;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MoneyTransferRequest {

    @NotNull
    @Min(0)
    private Long destAccountOwnerId;

    @NotNull
    @Min(0)
    private Long sourceBankAccount;

    @NotNull
    @Min(0)
    private Long destinationBankAccount;

    @NotNull
    @DecimalMin("0.0")
    private Double amount;

    @NotBlank
    private String answer;

    public MoneyTransferRequest() {
    }

    public MoneyTransferRequest(Long destAccountOwnerId, Long sourceBankAccount, Long destinationBankAccount, Double amount, @NotBlank String answer) {
        this.destAccountOwnerId = destAccountOwnerId;
        this.sourceBankAccount = sourceBankAccount;
        this.destinationBankAccount = destinationBankAccount;
        this.amount = amount;
        this.answer = answer;
    }

    public MoneyTransferRequest(Long destAccountOwnerId, Long sourceBankAccount, Long destinationBankAccount, Double amount) {
        this.destAccountOwnerId = destAccountOwnerId;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getDestAccountOwnerId() {
        return destAccountOwnerId;
    }

    public void setDestAccountOwnerId(Long destAccountOwnerId) {
        this.destAccountOwnerId = destAccountOwnerId;
    }
}