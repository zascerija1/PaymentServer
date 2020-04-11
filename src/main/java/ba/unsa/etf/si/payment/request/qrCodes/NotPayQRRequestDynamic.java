package ba.unsa.etf.si.payment.request.qrCodes;

import javax.validation.constraints.NotBlank;

public class NotPayQRRequestDynamic {

    @NotBlank
    private String receiptId;

    public NotPayQRRequestDynamic() {
    }

    public NotPayQRRequestDynamic(@NotBlank String receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }
}
