package ba.unsa.etf.si.payment.request.qrCodes;


import java.io.Serializable;
import java.util.UUID;

public class NotPayQRRequestStatic implements Serializable {

    private UUID transactionId;

    public NotPayQRRequestStatic() {
    }

    public NotPayQRRequestStatic(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionID) {
        this.transactionId = transactionID;
    }
}
