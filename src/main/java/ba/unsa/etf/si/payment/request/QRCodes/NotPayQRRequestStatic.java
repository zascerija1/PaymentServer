package ba.unsa.etf.si.payment.request.QRCodes;


import java.io.Serializable;

public class NotPayQRRequestStatic implements Serializable {

    private Long transactionId;

    public NotPayQRRequestStatic() {
    }

    public NotPayQRRequestStatic(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionID) {
        this.transactionId = transactionID;
    }
}
