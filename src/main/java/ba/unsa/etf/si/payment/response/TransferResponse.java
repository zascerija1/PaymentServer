package ba.unsa.etf.si.payment.response;

import java.time.LocalDateTime;

public class TransferResponse {
    private Long id;
    private String receives;
    private String sends;
    private LocalDateTime dateAndTime;
    private Double moneyAmount;

    public TransferResponse(Long id, String receives, String sends, LocalDateTime dateAndTime, Double moneyAmount) {
        this.id = id;
        this.receives = receives;
        this.sends = sends;
        this.dateAndTime = dateAndTime;
        this.moneyAmount = moneyAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceives() {
        return receives;
    }

    public void setReceives(String receives) {
        this.receives = receives;
    }

    public String getSends() {
        return sends;
    }

    public void setSends(String sends) {
        this.sends = sends;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
