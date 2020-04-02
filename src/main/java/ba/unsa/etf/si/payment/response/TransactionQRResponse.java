package ba.unsa.etf.si.payment.response;

public class TransactionQRResponse {

    private Long transactionID;
    private Double amount;
    private String service;

    public TransactionQRResponse(Long transactionID, Double amount, String service) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.service = service;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}