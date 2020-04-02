package ba.unsa.etf.si.payment.model;


import ba.unsa.etf.si.payment.annotation.CardValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "bank_accounts")
@Check( constraints = "balance >= 0")
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bank_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bank bank;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="dd.MM.yyyy")
    @Column(name = "expiry_date", nullable = false, updatable = false)
    private Date expiryDate;

    @Column(columnDefinition = "text")
    @Length(min = 3, max = 3)
    @CardValidation(message = "Card validation code is not valid!")
    private String cvc;

    @Column(columnDefinition = "text")
    @Length(min = 16, max = 16)
    @CardValidation(message = "Card number is not valid!")
    private String cardNumber;

    @Column(name = "balance", nullable = false)
    private Double balance;

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

    public Double getBalance() { return balance; }

    public void setBalance(Double balance) { this.balance = balance; }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
