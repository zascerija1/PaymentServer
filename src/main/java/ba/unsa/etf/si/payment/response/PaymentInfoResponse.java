package ba.unsa.etf.si.payment.response;

import javax.validation.constraints.NotBlank;

public class PaymentInfoResponse {

    @NotBlank
    private Long transactionId;
    @NotBlank
    private String service;
    @NotBlank
    private Double totalPrice;

    public PaymentInfoResponse(@NotBlank Long transactionId, @NotBlank String service, @NotBlank Double totalPrice) {
        this.transactionId = transactionId;
        this.service = service;
        this.totalPrice = totalPrice;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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
