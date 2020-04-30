package ba.unsa.etf.si.payment.response.transferResponse;

import ba.unsa.etf.si.payment.util.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class TransferResponse {
    private UUID id;
    private String destCardNumber;
    private String sourceCardNumber;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Sarajevo")
    private Date transferDateAndTime;
    private Double amount;
    private PaymentStatus paymentStatus;

    public TransferResponse() {
    }

    public TransferResponse(UUID id, Date transferDateAndTime, Double amount, PaymentStatus paymentStatus) {
        this.id = id;
        this.transferDateAndTime = transferDateAndTime;
        this.amount = amount;
        this.paymentStatus=paymentStatus;
    }

    public TransferResponse(UUID id, String destCardNumber, String sourceCardNumber, Date transferDateAndTime, Double amount, PaymentStatus paymentStatus) {
        this.id = id;
        this.destCardNumber = destCardNumber;
        this.sourceCardNumber = sourceCardNumber;
        this.transferDateAndTime = transferDateAndTime;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDestCardNumber() {
        return destCardNumber;
    }

    public void setDestCardNumber(String destCardNumber) {
        this.destCardNumber = destCardNumber;
    }

    public String getSourceCardNumber() {
        return sourceCardNumber;
    }

    public void setSourceCardNumber(String sourceCardNumber) {
        this.sourceCardNumber = sourceCardNumber;
    }

    public Date getTransferDateAndTime() {
        return transferDateAndTime;
    }

    public void setTransferDateAndTime(Date transferDateAndTime) {
        this.transferDateAndTime = transferDateAndTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
