package ba.unsa.etf.si.payment.request.qrCodes;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

public class NotPayQRRequestStatic implements Serializable {

    @NotNull
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
