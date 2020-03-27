package ba.unsa.etf.si.payment.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends AuditModel {

    @Id
    @GeneratedValue(generator = "bank_generator")
    @SequenceGenerator(
            name = "bank_generator",
            sequenceName = "bank_sequence"
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String  accountOwner;

    @Column(columnDefinition = "text")
    private String  bankName;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="dd.MM.yyyy")
    @Column(name = "expiry_date", nullable = false, updatable = false)
    private Date expiryDate;

    @Column(columnDefinition = "text")
    @Length(min = 3, max = 3)
    private String cvc;

    @Column(columnDefinition = "text")
    @Length(min = 16, max = 16)
    private String cardNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
