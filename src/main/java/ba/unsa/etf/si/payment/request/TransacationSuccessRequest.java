package ba.unsa.etf.si.payment.request;

import java.io.Serializable;

public class TransacationSuccessRequest implements Serializable {

private String status;
private String message;

    public TransacationSuccessRequest(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
