package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class PayQRRequest {

    @NotBlank
    private Boolean proceed;

    @NotBlank
    private Long bankAccountId;

    @NotBlank
    private Long transactionId;

    public PayQRRequest(@NotBlank Boolean proceed, @NotBlank Long bankAccountId, @NotBlank Long transactionId) {
        this.proceed = proceed;
        this.bankAccountId = bankAccountId;
        this.transactionId = transactionId;
    }

    public Boolean getProceed() {
        return proceed;
    }

    public void setProceed(Boolean proceed) {
        this.proceed = proceed;
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