package ba.unsa.etf.si.payment.response.Transfer;

import java.util.Date;
import java.util.UUID;

public class TransferResponse {
    private UUID id;
    private String destCardNumber;
    private String sourceCardNumber;
    private Date transferDateAndTime;
    private Double moneyAmount;

    public TransferResponse(UUID id, String destCardNumber, String sourceCardNumber, Date transferDateAndTime, Double moneyAmount) {
        this.id = id;
        this.destCardNumber = destCardNumber;
        this.sourceCardNumber = sourceCardNumber;
        this.transferDateAndTime = transferDateAndTime;
        this.moneyAmount = moneyAmount;
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

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
