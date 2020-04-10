package ba.unsa.etf.si.payment.response.transactionResponse;

import java.util.UUID;

public class TransactionSubmitResponse {


    private UUID transactionId;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private Double totalPrice;
    private String service;

    public TransactionSubmitResponse(UUID transactionId, Double amount, String service) {
        this.transactionId = transactionId;
        this.totalPrice = amount;
        this.service = service;
    }



    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}