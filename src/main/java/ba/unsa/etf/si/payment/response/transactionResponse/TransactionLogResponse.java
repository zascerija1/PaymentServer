package ba.unsa.etf.si.payment.response.transactionResponse;

import ba.unsa.etf.si.payment.util.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class TransactionLogResponse {
    private UUID transactionLogId;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Sarajevo")
    private Date logDateAndTime;
    private PaymentStatus paymentStatus;
    private String cardNumber;

    public TransactionLogResponse() {
    }

    public TransactionLogResponse(UUID transactionLogId, Date logDateAndTime, PaymentStatus paymentStatus) {
        this.transactionLogId = transactionLogId;
        this.logDateAndTime = logDateAndTime;
        this.paymentStatus = paymentStatus;
    }

    public TransactionLogResponse(UUID transactionLogId, Date logDateAndTime, PaymentStatus paymentStatus, String cardNumber) {
        this.transactionLogId = transactionLogId;
        this.logDateAndTime = logDateAndTime;
        this.paymentStatus = paymentStatus;
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public UUID getTransactionLogId() {
        return transactionLogId;
    }

    public void setTransactionLogId(UUID transactionLogId) {
        this.transactionLogId = transactionLogId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getLogDateAndTime() {
        return logDateAndTime;
    }

    public void setLogDateAndTime(Date logDateAndTime) {
        this.logDateAndTime = logDateAndTime;
    }
}
