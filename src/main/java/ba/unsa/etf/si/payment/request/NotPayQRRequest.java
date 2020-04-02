package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class NotPayQRRequest {

    //todo made this, but might delete later if we decide to have one route for paying confirmation...

    @NotBlank
    private Boolean proceed;

    @NotBlank
    private Long transactionId;

    public NotPayQRRequest(@NotBlank Boolean proceed, @NotBlank Long transactionId) {
        this.proceed = proceed;
        this.transactionId = transactionId;
    }

    public Boolean getProceed() {
        return proceed;
    }

    public void setProceed(Boolean proceed) {
        this.proceed = proceed;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionID) {
        this.transactionId = transactionID;
    }
}
