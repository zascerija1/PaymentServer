package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class PayQRRequest {

    @NotBlank
    private Boolean proceed;

    @NotBlank
    private Integer bankAccount;

    @NotBlank
    private Long transactionID;

    public PayQRRequest(@NotBlank Boolean proceed, @NotBlank Integer bankAccount, @NotBlank Long transactionID) {
        this.proceed = proceed;
        this.bankAccount = bankAccount;
        this.transactionID = transactionID;
    }

    public Boolean getProceed() {
        return proceed;
    }

    public void setProceed(Boolean proceed) {
        this.proceed = proceed;
    }

    public Integer getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Integer bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }
}