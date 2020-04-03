package ba.unsa.etf.si.payment.response;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class MainInfoResponse implements Serializable {
    @NotBlank
    private String receiptId;
    @NotBlank
    private String service;
    @NotBlank
    private Double totalPrice;

    public MainInfoResponse() {
    }

    public MainInfoResponse(String receiptId, String service, Double totalPrice) {
        this.receiptId = receiptId;
        this.service = service;
        this.totalPrice = totalPrice;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
