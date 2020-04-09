package ba.unsa.etf.si.payment.response;

import java.util.Date;

public class TransferResponse {
    private Long id;
    private String receives;
    private String sends;
    private Date dateAndTime;
    private Double moneyAmount;

    public TransferResponse(Long id, String receives, String sends, Date dateAndTime, Double moneyAmount) {
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

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
