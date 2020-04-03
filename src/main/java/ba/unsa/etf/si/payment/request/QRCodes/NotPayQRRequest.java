package ba.unsa.etf.si.payment.request.QRCodes;


import java.io.Serializable;

public class NotPayQRRequest implements Serializable {

    private Long transactionId;

    public NotPayQRRequest() {
    }

    public NotPayQRRequest(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionID) {
        this.transactionId = transactionID;
    }
}
