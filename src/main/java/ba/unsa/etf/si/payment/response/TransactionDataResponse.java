package ba.unsa.etf.si.payment.response;

import java.util.Date;

public class TransactionDataResponse {

    private String cardNumber;
    private String merchantName;
    private Date date;
    private Double totalPrice;
    private String service;

    public TransactionDataResponse() {
    }

    public TransactionDataResponse(String cardNumber, String merchantName, Date date, Double totalPrice, String service) {
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
}
