package ba.unsa.etf.si.payment.request;

import ba.unsa.etf.si.payment.annotation.CardValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class BankAccountRequest {
    private String accountOwner;
    private String bankName;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="dd.MM.yyyy", timezone = "Europe/Sarajevo")
    private Date expiryDate;
    @Length(min = 3, max = 3)
    @CardValidation(message = "Card validation code is not valid!")
    private String cvc;
    @Length(min = 16, max = 16)
    @CardValidation(message = "Card number is not valid!")
    private String cardNumber;

    public BankAccountRequest() {
    }

    public BankAccountRequest(String accountOwner, String bankName, Date expiryDate, @Length(min = 3, max = 3) String cvc, @Length(min = 16, max = 16) String cardNumber) {
        this.accountOwner = accountOwner;
        this.bankName = bankName;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
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

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
