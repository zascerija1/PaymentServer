package ba.unsa.etf.si.payment.response;

import java.util.Date;

public class BankAccountDataResponse {

    private Long id;

    private String  accountOwner;

    private String  bankName;

    private Date expiryDate;

    private String cardNumber;

    public BankAccountDataResponse() {
    }

    public BankAccountDataResponse(Long id, String accountOwner, String bankName, Date expiryDate, String cardNumber) {
        this.id = id;
        this.accountOwner = accountOwner;
        this.bankName = bankName;
        this.expiryDate = expiryDate;
        this.cardNumber = cardNumber;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
