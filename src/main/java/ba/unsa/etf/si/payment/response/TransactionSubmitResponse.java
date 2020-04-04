package ba.unsa.etf.si.payment.response;

public class TransactionSubmitResponse {

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    private Long transactionId;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private Double totalPrice;
    private String service;

    public TransactionSubmitResponse(Long transactionId, Double amount, String service) {
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
}