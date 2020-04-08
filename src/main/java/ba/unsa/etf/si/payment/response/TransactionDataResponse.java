package ba.unsa.etf.si.payment.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class TransactionDataResponse {

    private UUID transactionId;
    private String cardNumber;
    private String merchantName;
    @JsonFormat(timezone="Europe/Sarajevo")
    private Date date;
    private Double totalPrice;
    private String service;

    public TransactionDataResponse() {
    }

    public TransactionDataResponse(UUID transactionId, String cardNumber, String merchantName, Date date, Double totalPrice, String service) {
        this.transactionId=transactionId;
        this.cardNumber = cardNumber;
        this.merchantName = merchantName;
        this.date = date;
        this.totalPrice = totalPrice;
        this.service = service;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
