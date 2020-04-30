package ba.unsa.etf.si.payment.request.qrCodes;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NotPayQRRequestDynamic {

    @NotBlank
    private String receiptId;

    @NotBlank
    private String businessName;

    @NotBlank
    private String service;

    @NotNull
    @DecimalMin("0.0")
    private Double totalPrice;


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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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
