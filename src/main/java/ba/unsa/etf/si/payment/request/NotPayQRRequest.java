package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class NotPayQRRequest {

    //todo made this, but might delete later if we decide to have one rute for paying confirmation...

    @NotBlank
    private Boolean proceed;

    @NotBlank
    private Long transactionID;

    public NotPayQRRequest(@NotBlank Boolean proceed, @NotBlank Long transactionID) {
        this.proceed = proceed;
        this.transactionID = transactionID;
    }

    public Boolean getProceed() {
        return proceed;
    }

    public void setProceed(Boolean proceed) {
        this.proceed = proceed;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }
}
